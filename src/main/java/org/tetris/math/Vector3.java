package org.tetris.math;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Vector3 {
    public float x;
    public float y;
    public float z;

    public Vector3() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }
    public Vector3(float t) {
        x = t;
        y = t;
        z = t;
    }

    public Vector3(Vector2 vector) {
        x = vector.x;
        y = vector.y;
        z = 1.0f;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public static Vector3 normalize(Vector3 vector)  {
        float length = vector.length();

        return new Vector3(vector.x / length, vector.y / length, vector.z / length);
    }
    public void normalize() {
        float length = length();

        x = x / length;
        y = y / length;
        z = z / length;
    }

    public static float dot(Vector3 left, Vector3 right) {
        return (left.x * right.x) + (left.y * right.y) + (left.z * right.z);
    }

    public float dot(Vector3 right) {
        return dot(this, right);
    }

    public static Vector3 cross(Vector3 left, Vector3 right) {
        return new Vector3(
            (left.y * right.z) - (left.z * right.y),
            (left.z * right.x) - (left.x * right.z),
            (left.x * right.y) - (left.y * right.x)
        );
    }

    public Vector3 cross(Vector3 right) {
        return cross(this, right);
    }

    public static Vector3 add(Vector3 left, Vector3 right) {
        return new Vector3(left.x + right.x, left.y + right.y, left.z + right.z);
    }

    public static Vector3 sub(Vector3 left, Vector3 right) {
        return new Vector3(left.x - right.x, left.y - right.y, left.z - right.z);
    }

    public static Vector3 getFront() {
        return new Vector3(0.0f, 0.0f, 1.0f);
    }

    public static Vector3 getRight() {
        return new Vector3(1.0f, 0.0f, 0.0f);
    }

    public static Vector3 getUp() {
        return new Vector3(0.0f, 1.0f, 0.0f);
    }

    @Override
    public String toString() {
        return String.format(
                "|%.2f, %6.2f, %6.2f|\n",
                x, y, z
        );
    }
}
