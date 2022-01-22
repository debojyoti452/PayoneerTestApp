package com.example.sdk.utils;

public class InputRenderer {
    public static Integer renderInputType(String type) {
        switch (type) {
            case "numeric": {
                return 0x00000002;
            }

            case "integer": {
                return 0x00001002;
            }

            case "string": {
                return 0x00002001;
            }

            default: {
                return 0x00000000;
            }
        }
    }

    public static Integer renderInputIme(int type) {
        switch (type) {
            case 0: {
                return 0x00000005;
            }

            case 1: {
                return 0x00000006;
            }

            default: {
                return 0x00000000;
            }
        }
    }
}
