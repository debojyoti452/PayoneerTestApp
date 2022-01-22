package com.example.sdk.utils;

import java.util.Arrays;

public enum CreditCardType {
    // 4
    VISA("Visa", "icon_visa",
            13, 19,
            new int[]{4},
            new int[]{4, 4, 4, 4, 4}
    ),
    // 4026, 417500, 4405, 4508, 4844, 4913, 4917
    VISA_ELECTRON("Visa Electron", "icon_visa_electron",
            16, 16,
            new int[]{4026, 417500, 4405, 4508, 4844, 4913, 4917},
            new int[]{4, 4, 4, 4}
    ),
    // 34, 37
    AMERICAN_EXPRESS("American Express", "icon_american_express",
            15, 15,
            new int[]{34, 37},
            new int[]{4, 6, 5}
    ),
    // 51‑55, 222100‑272099
    MASTERCARD("MasterCard", "icon_mastercard",
            16, 16,
            concat(intRange(51, 55), intRange(222100, 272099)),
            new int[]{4, 4, 4, 4}
    ),
    // 6011, 622126‑622925, 644‑649, 65
    DISCOVER("MasterCard", "icon_mastercard",
            16, 16,
            append(concat(intRange(622126, 622925), intRange(644, 649)), 6011, 65),
            new int[]{4, 4, 4, 4}
    ),
    // 3528‑3589
    JCB("JCB", "icon_jcb",
            16, 16,
            intRange(3528, 3589),
            new int[]{4, 4, 4, 4}
    ),
    // 1
    UATP("UATP", "icon_uatp",
            15, 15,
            new int[]{1},
            new int[]{4, 5, 6}
    ),
    // 5019
    DANKORT("Dankort", "icon_dankort",
            16, 16,
            new int[]{5019},
            new int[]{4, 4, 4, 4}
    );

    public static final int[] DEFAULT_BLOCK_LENGTHS = new int[]{4, 4, 4, 4, 4};
    public static final int DEFAULT_MAX_LENGTH = 4 * 5;

    private String[] prefixs;
    private int[] blockLengths;
    private String name;
    // Name of Image in "drawable" folder.
    private String imageResourceName;

    private int minLength;
    private int maxLength;

    CreditCardType(String name, String imageResourceName,
                   int minLength, int maxLength,
                   int[] intPrefixs, int[] blockLengths) {
        this.name = name;
        this.imageResourceName = imageResourceName;
        if (intPrefixs != null) {
            this.prefixs = new String[intPrefixs.length];
            for (int i = 0; i < intPrefixs.length; i++) {
                this.prefixs[i] = String.valueOf(intPrefixs[i]);
            }
        }
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.blockLengths = blockLengths;
    }

    public String getName() {
        return name;
    }

    public int getMinLength() {
        return minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public String[] getPrefix() {
        return this.prefixs;
    }

    public int[] getBlockLengths() {
        return this.blockLengths;
    }

    public String getImageResourceName() {
        return this.imageResourceName;
    }

    private static int[] intRange(int from, int to) {
        int length = to - from + 1;
        int[] ret = new int[length];
        for (int i = from; i < to + 1; i++) {
            ret[i - from] = i;
        }
        return ret;
    }

    private static int[] concat(int[] first, int[] second) {
        int[] both = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, both, first.length, second.length);
        return both;
    }

    private static int[] append(int[] first, int... value) {
        if (value == null || value.length == 0) {
            return first;
        }
        int[] both = Arrays.copyOf(first, first.length + value.length);

        System.arraycopy(value, 0, both, first.length, value.length);
        return both;
    }

    public static CreditCardType detect(String creditCardNumber) {
        if (creditCardNumber == null || creditCardNumber.isEmpty()) {
            return null;
        }
        CreditCardType found = null;
        int max = 0;
        for (CreditCardType type : CreditCardType.values()) {
            for (String prefix : type.prefixs) {
                if (creditCardNumber.startsWith(prefix) && prefix.length() > max) {
                    found = type;
                    max = prefix.length();
                }
            }
        }
        return found;
    }

}
