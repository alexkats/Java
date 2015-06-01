package ru.ifmo.ctddev.kacman.crawler;

import info.kgeorgiy.java.advanced.crawler.Downloader;
import info.kgeorgiy.java.advanced.crawler.URLUtils;

import java.net.MalformedURLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ControlThreads {
    public Downloader downloader;
    public final int perHost;
    public ExecutorService downloadingThreads;
    public ExecutorService extractingThreads;
    public ConcurrentHashMap<String, Semaphore> hosts;

    public ControlThreads (Downloader downloader, int downloaders, int extractors, int perHost) {
        this.downloader = downloader;
        this.perHost = perHost;
        downloadingThreads = Executors.newFixedThreadPool(downloaders);
        extractingThreads = Executors.newFixedThreadPool(extractors);
        hosts = new ConcurrentHashMap<>();
    }

    public void acquire(String url) {
        try {
            String host = URLUtils.getHost(url);

            if (!hosts.containsKey(host)) {
                hosts.put(host, new Semaphore(perHost));
            }

            hosts.get(host).acquire();
        } catch (MalformedURLException | InterruptedException ignored) {

        }
    }

    public void release(String url) {
        try {
            String host = URLUtils.getHost(url);
            hosts.get(host).release();
        } catch (MalformedURLException ignored) {

        }
    }
}