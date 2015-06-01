package ru.ifmo.ctddev.kacman.crawler;

import info.kgeorgiy.java.advanced.crawler.Crawler;
import info.kgeorgiy.java.advanced.crawler.Document;
import info.kgeorgiy.java.advanced.crawler.Downloader;
import info.kgeorgiy.java.advanced.crawler.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class WebCrawler implements Crawler {

    private ControlThreads controlThreads;

    public WebCrawler(Downloader downloader, int downloaders, int extractors, int perHost) {
        controlThreads = new ControlThreads(downloader, downloaders, extractors, perHost);
    }

    @Override
    public Result download(String s, int i) {
        Resources resources = new Resources();

        try {
            resources.getLock().lock();
            controlThreads.downloadingThreads.submit(new DownloadWorker(s, 1, i, resources));

            while (resources.getActiveTasks().get() > 0) {
                resources.getIsDone().await();
            }
        } catch (InterruptedException ignored) {

        } finally {
            resources.getLock().unlock();
        }

        return new Result(new ArrayList<>(resources.getLinks()), resources.getErrors());
    }

    public class DownloadWorker implements Runnable {

        private final String url;
        private final int depth;
        private final int maxDepth;
        private Resources resources;

        public DownloadWorker(String url, int depth, int maxDepth, Resources resources) {
            this.url = url;
            this.depth = depth;
            this.maxDepth = maxDepth;
            this.resources = resources;
            this.resources.getActiveTasks().incrementAndGet();
        }

        @Override
        public void run() {
            try {
                if (resources.getDownloadedPages().add(url)) {
                    controlThreads.acquire(url);
                    Document document = controlThreads.downloader.download(url);
                    controlThreads.release(url);
                    resources.getLinks().add(url);

                    if (depth < maxDepth) {
                        controlThreads.extractingThreads.submit(new ExtractWorker(document, depth + 1, maxDepth, resources));
                    }
                }
            } catch (IOException e) {
                resources.getErrors().put(url, e);
            } finally {
                decrementActiveTasks(resources);
            }
        }
    }

    public class ExtractWorker implements Runnable {

        private final Document document;
        private final int depth;
        private final int maxDepth;
        private Resources resources;

        public ExtractWorker(Document document, int depth, int maxDepth, Resources resources) {
            this.document = document;
            this.depth = depth;
            this.maxDepth = maxDepth;
            this.resources = resources;
            this.resources.getActiveTasks().incrementAndGet();
        }

        @Override
        public void run() {
            try {
                List<String> links = document.extractLinks();

                for (String link : links) {
                    controlThreads.downloadingThreads.submit(new DownloadWorker(link, depth, maxDepth, resources));
                }
            } catch (IOException ignored) {

            } finally {
                decrementActiveTasks(resources);
            }
        }
    }

    private void decrementActiveTasks(Resources resources) {
        if (resources.getActiveTasks().decrementAndGet() == 0) {
            try {
                resources.getLock().lock();
                resources.getIsDone().signal();
            } finally {
                resources.getLock().unlock();
            }
        }
    }

    @Override
    public void close() {
        controlThreads.downloadingThreads.shutdown();
        controlThreads.extractingThreads.shutdown();
    }
}