package com.polling.sse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope("singleton")
public class SseEngine {

  private static long TIMEOUT = 1800000L;

  private final Map<String, HashSet<SseEmitter>> emitters = new ConcurrentHashMap<>();

  private void config(SseEmitter emitter, String poll_id) {
    if (emitter != null) {
      emitter.onCompletion(() -> {
        System.out.println("Emitter " + emitter.toString() + " COMPLETED!");
        HashSet<SseEmitter> set = this.emitters.get(poll_id);
        set.remove(emitter);
        if (set.isEmpty()) {
          emitters.remove(poll_id);
        }
      });
      emitter.onTimeout(() -> {
        System.out.println("Emitter " + emitter.toString() + " TIMEOUT!");
        HashSet<SseEmitter> set = this.emitters.get(poll_id);
        set.remove(emitter);
        if (set.isEmpty()) {
          emitters.remove(poll_id);
        }
      });
    }
  }

  public long getTimeout() {
    return TIMEOUT;
  }

  public Map<String, HashSet<SseEmitter>> getEmitters() {
    return emitters;
  }

  public SseEmitter addEmitter(String poll_id) {
    SseEmitter emitter = new SseEmitter(this.getTimeout());
    System.out.println("--a new SseEmitter is created");
    this.config(emitter, poll_id);
    if (!this.emitters.containsKey(poll_id)) {
      this.emitters.put(poll_id, new HashSet<SseEmitter> ());
    }
    HashSet<SseEmitter> set = this.emitters.get(poll_id);
    set.add(emitter);
    return emitter;
  }

  public void dispatchByPollId(String poll_id, Object parcel) {
    HashSet<SseEmitter> set = this.emitters.get(poll_id);

    // if there are no one observing this poll, we don't need to dispatch any messages.
    if (set == null || set.isEmpty()) return;

    List<SseEmitter> deadEmitters = new ArrayList<>();
    for (SseEmitter emitter : set) {
      try {
        emitter.send(parcel);
      } catch (Exception e) {
        deadEmitters.add(emitter);
      }
    }
    set.removeAll(deadEmitters);
  }
}