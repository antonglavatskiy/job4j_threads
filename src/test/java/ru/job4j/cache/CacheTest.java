package ru.job4j.cache;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base user = new Base(1, 0);
        assertThat(cache.add(user)).isTrue();
        assertThat(cache.add(user)).isFalse();
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base user = new Base(1, 0);
        cache.add(user);
        cache.delete(user);
        assertThat(cache.add(user)).isTrue();
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        Base user = new Base(1, 0);
        user.setName("User");
        cache.add(user);
        user.setName("Admin");
        assertThat(cache.update(user)).isTrue();
        assertThat(cache.getMemory().get(user.getId()).getVersion()).isEqualTo(1);
    }
}