package io.realmit.interfass.enums;

public enum NegativeCharactersEnum {

    //Spacing Characters
    NEG1("\uF801"),
    NEG2("\uF802"),
    NEG4("\uF804"),
    NEG8("\uF808"),
    NEG16("\uF809"),
    NEG32("\uF80A"),
    NEG64("\uF80B"),
    NEG128("\uF80C"),
    NEG256("\uF80D"),
    NEG512("\uF80E"),
    NEG1024("\uF80F"),

    POS1("\uF821"),
    POS2("\uF822"),
    POS4("\uF824"),
    POS8("\uF828"),
    POS16("\uF829"),
    POS32("\uF82A"),
    POS64("\uF82B"),
    POS128("\uF82C"),
    POS256("\uF82D"),
    POS512("\uF82E"),
    POS1024("\uF82F"),

    MENU_CONTAINER_27("\uF001"),
    MENU_BUTTON("\uF002"),
    MENU_TEST("\uF003");

    public final String literal;

    NegativeCharactersEnum(String literal) {
        this.literal = literal;
    }

    @Override
    public String toString() {
        return this.literal;
    }

    private enum SpacingCharacters {
        NEG1(-1, NegativeCharactersEnum.NEG1),
        NEG2(-2, NegativeCharactersEnum.NEG2),
        NEG4(-4, NegativeCharactersEnum.NEG4),
        NEG8(-8, NegativeCharactersEnum.NEG8),
        NEG16(-16, NegativeCharactersEnum.NEG16),
        NEG32(-32, NegativeCharactersEnum.NEG32),
        NEG64(-64, NegativeCharactersEnum.NEG64),
        NEG128(-128, NegativeCharactersEnum.NEG128),
        NEG256(-256, NegativeCharactersEnum.NEG256),
        NEG512(-512, NegativeCharactersEnum.NEG512),
        NEG1024(-1024, NegativeCharactersEnum.NEG1024),

        POS1(1, NegativeCharactersEnum.POS1),
        POS2(2, NegativeCharactersEnum.POS2),
        POS4(4, NegativeCharactersEnum.POS4),
        POS8(8, NegativeCharactersEnum.POS8),
        POS16(16, NegativeCharactersEnum.POS16),
        POS32(32, NegativeCharactersEnum.POS32),
        POS64(64, NegativeCharactersEnum.POS64),
        POS128(128, NegativeCharactersEnum.POS128),
        POS256(256, NegativeCharactersEnum.POS256),
        POS512(512, NegativeCharactersEnum.POS512),
        POS1024(1024, NegativeCharactersEnum.POS1024);

        private final int weight;
        private final NegativeCharactersEnum charRef;

        SpacingCharacters(int weight, NegativeCharactersEnum charRef) {
            this.weight = weight;
            this.charRef = charRef;
        }
    }

    public static NegativeCharactersEnum getCharacterByWeight(int weight) {
        for (SpacingCharacters ch : SpacingCharacters.values()) {
            if (ch.weight == weight)
                return ch.charRef;
        }
        return null;
    }

    public static String getSpacing(int pixelAmount) {
        //convert amount to binary string
        String binary = new StringBuilder(Integer.toBinaryString(Math.abs(pixelAmount))).reverse().toString();
        StringBuilder sb = new StringBuilder();
        char[] chArr = binary.toCharArray();
        for (int index = 0; index < chArr.length; index++) {
            char ch = chArr[index];
            if (ch == '0') continue;

            int weight = (int) Math.pow(2, index);
            //if we are getting negative, flip weight
            weight = pixelAmount < 0 ? -weight : weight;
            NegativeCharactersEnum ref = getCharacterByWeight(weight);

            if (ref != null)
                sb.append(ref.literal);
        }
        return sb.toString();
    }

    public static String getNeg(int pixelAmount) {
        return getSpacing(-Math.abs(pixelAmount));
    }

    public static String getPos(int pixelAmount) {
        return getSpacing(Math.abs(pixelAmount));
    }
}
