package uet.oop.bomberman.entities.character.enemy.AI;

public class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    // Nếu bạn muốn, bạn cũng có thể thêm các phương thức setKey và setValue:
    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }
}