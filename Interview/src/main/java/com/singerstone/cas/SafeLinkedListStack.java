package com.singerstone.cas;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 模拟获取锁的原理
 * 当一个线程把 lock 从 false 切换到 true 就认为已经获取到了锁
 * 获取锁的这个过程一定要是原子的操作
 * 释放锁的过程不一定要是原子的操作
 *
 */
public class SafeLinkedListStack<E> implements Stack<E> {
    private volatile AtomicBoolean lock = new AtomicBoolean(false);

    Node<E> mHead;
    private volatile int mSize;

    public SafeLinkedListStack() {
        mHead = new Node(null);
        mSize = 0;
    }

    @Override
    public int size() {
        do {
            if (lock.compareAndSet(false, true)) {
                lock.set(false);
                return mSize;
            }
        } while (true);
    }

    @Override
    public boolean isEmpty() {
        do {
            if (lock.compareAndSet(false, true)) {
                lock.set(false);
                return mSize == 0;
            }
        } while (true);


    }

    @Override
    public void push(E e) {
        Node<E> newNode = new Node<>(e);
        do {
            if (lock.compareAndSet(false, true)) {
                SleepUtil.sleep(10); //强制切线程
                newNode.next = mHead.next;
                SleepUtil.sleep(50); //强制切线程
                mHead.next = newNode;
                mSize++;
                lock.set(false);
                break;
            }
        } while (true);
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stank is Empty!");
        }
        do {
            if (lock.compareAndSet(false, true)) {
                Node<E> p = mHead.next;
                E result = p.element;
                mHead.next = p.next;
                mSize--;
                // 这里其实没有必要 compareAndSet
                lock.set(false);
                return result;
            }
        } while (true);

    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stank is Empty!");
        }
        Node<E> p = mHead.next;
        E result = p.element;
        return result;
    }

    @Override
    public String toString() {
        String result = "";
        Node temp = mHead.next;
        while (temp != null) {
            result += temp.element;
            temp = temp.next;

        }
        return result;
    }
}