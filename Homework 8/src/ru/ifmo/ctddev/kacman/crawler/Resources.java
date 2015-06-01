package ru.ifmo.ctddev.kacman.crawler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Resources {

    private ReentrantLock lock;
    private Condition isDone;
    private AtomicInteger activeTasks;
    private ConcurrentSkipListSet<String> downloadedPages;
    private ConcurrentLinkedQueue<String> links;
    private ConcurrentHashMap<String, IOException> errors;

    public Resources() {
        this.lock = new ReentrantLock();
        this.isDone = lock.newCondition();
        this.activeTasks = new AtomicInteger();
        this.downloadedPages = new ConcurrentSkipListSet<>();
        this.links = new ConcurrentLinkedQueue<>();
        this.errors = new ConcurrentHashMap<>();
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public Condition getIsDone() {
        return isDone;
    }

    public AtomicInteger getActiveTasks() {
        return activeTasks;
    }

    public ConcurrentSkipListSet getDownloadedPages() {
        return downloadedPages;
    }

    public ConcurrentLinkedQueue getLinks() {
        return links;
    }

    public ConcurrentHashMap getErrors() {
        return errors;
    }
}