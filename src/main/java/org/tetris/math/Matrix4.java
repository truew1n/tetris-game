package org.tetris.math;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Matrix4 {
    private float[][] values;

    public Matrix4() {
        values = new float[4][4];
    }
    public Matrix4(float t) {
        values = new float[4][4];

        values[0][0] = t;
        values[1][1] = t;
        values[2][2] = t;
        values[3][3] = t;
    }

    public static Matrix4 getIdentity() {
        return new Matrix4(1.0f);
    }

    public static Matrix4 getOrtographic(float near, float far, float top, float bottom, float left, float right) {
        Matrix4 resultMatrix = new Matrix4();

        float rml = right - left;
        float tmb = top - bottom;
        float fmn = far - near;

        resultMatrix.values[0][0] = 2 / rml;
        resultMatrix.values[1][1] = 2 / tmb;
        resultMatrix.values[2][2] = -2 / fmn;
        resultMatrix.values[3][3] = 1;

        resultMatrix.values[0][3] = (right + left) / -rml;
        resultMatrix.values[1][3] = (top + bottom) / -tmb;
        resultMatrix.values[2][3] = (far + near) / -fmn;

        return resultMatrix;
    }

    public static Matrix4 getView(Vector3 eye, float Yaw, float Pitch) {
        Matrix4 resultMatrix = new Matrix4();

        Vector3 front = new Vector3(
                (float) (Math.cos(Math.toRadians(Yaw)) * Math.cos(Math.toRadians(Pitch))),
                (float) Math.sin(Math.toRadians(Pitch)),
                (float) (Math.sin(Math.toRadians(Yaw)) * Math.cos(Math.toRadians(Pitch)))
        );
        front.normalize();

        Vector3 target = Vector3.add(eye, front);

        Vector3 forward = Vector3.normalize(Vector3.sub(eye, target));
        Vector3 right = Vector3.cross(Vector3.getUp(), forward);
        Vector3 up = Vector3.cross(forward, right);

        resultMatrix.values[0][0] = right.x;
        resultMatrix.values[0][1] = right.y;
        resultMatrix.values[0][2] = right.z;

        resultMatrix.values[1][0] = up.x;
        resultMatrix.values[1][1] = up.y;
        resultMatrix.values[1][2] = up.z;

        resultMatrix.values[2][0] = -forward.x;
        resultMatrix.values[2][1] = -forward.y;
        resultMatrix.values[2][2] = -forward.z;

        resultMatrix.values[0][3] = -Vector3.dot(right, eye);
        resultMatrix.values[1][3] = -Vector3.dot(up, eye);
        resultMatrix.values[2][3] = -Vector3.dot(forward, eye);

        resultMatrix.values[3][3] = 1.0f;

        return resultMatrix;
    }


    public static Matrix4 getTranslation(float x, float y, float z) {
        Matrix4 resultMatrix = getIdentity();

        resultMatrix.values[0][3] = x;
        resultMatrix.values[1][3] = y;
        resultMatrix.values[2][3] = z;

        return resultMatrix;
    }

    public static Matrix4 getScaling(float x, float y, float z) {
        Matrix4 resultMatrix = getIdentity();

        resultMatrix.values[0][0] *= x;
        resultMatrix.values[1][1] *= y;
        resultMatrix.values[2][2] *= z;

        return resultMatrix;
    }

    public static Matrix4 getRotationX(float theta) {
        Matrix4 resultMatrix = getIdentity();

        resultMatrix.values[1][1] = (float) Math.cos(theta);
        resultMatrix.values[1][2] = (float) -Math.sin(theta);
        resultMatrix.values[2][1] = (float) Math.sin(theta);
        resultMatrix.values[2][2] = (float) Math.cos(theta);

        return  resultMatrix;
    }

    public static Matrix4 getRotationY(float theta) {
        Matrix4 resultMatrix = getIdentity();

        resultMatrix.values[0][0] = (float) Math.cos(theta);
        resultMatrix.values[0][2] = (float) Math.sin(theta);
        resultMatrix.values[2][0] = (float) -Math.sin(theta);
        resultMatrix.values[2][2] = (float) Math.cos(theta);

        return  resultMatrix;
    }

    public static Matrix4 getRotationZ(float theta) {
        Matrix4 resultMatrix = getIdentity();

        resultMatrix.values[0][0] = (float) Math.cos(theta);
        resultMatrix.values[0][1] = (float) -Math.sin(theta);
        resultMatrix.values[1][0] = (float) Math.sin(theta);
        resultMatrix.values[1][1] = (float) Math.cos(theta);

        return  resultMatrix;
    }

    public static Matrix4 multiply(Matrix4 left, Matrix4 right) {
        Matrix4 resultMatrix = new Matrix4();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    resultMatrix.values[i][j] += left.values[i][k] * right.values[k][j];
                }
            }
        }

        return resultMatrix;
    }

    public Matrix4 multiply(Matrix4 right) {
        return multiply(this, right);
    }

    public static Vector4 multiply(Matrix4 left, Vector4 right) {
        Vector4 resultVector = new Vector4();

        for (int i = 0; i < 4; i++) {
            resultVector.x += left.values[0][i] * right.x;
            resultVector.y += left.values[1][i] * right.y;
            resultVector.z += left.values[2][i] * right.z;
            resultVector.w += left.values[3][i] * right.w;
        }

        return resultVector;
    }

    public Vector4 multiply(Vector4 right) {
        return multiply(this, right);
    }

    public float[] flat() {
        float[] flatArray = new float[16];

        int index = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                flatArray[index] = values[i][j];
                index++;
            }
        }

        return flatArray;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            builder.append(String.format(
                "|%.2f, %6.2f, %6.2f, %6.2f|\n",
                values[i][0], values[i][1], values[i][2], values[i][3]
            ));
        }

        return builder.toString();
    }
}
