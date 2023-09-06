package com.animalplatform.platform.utils;

import java.math.BigDecimal;



public class NumberRange {

    public interface SymbolType {
        public final static int UNKNOWN = 0;
        public final static int GTEQ = UNKNOWN + 1;
        public final static int GT = GTEQ + 1;
        public final static int EQ = GT + 1;
        public final static int LTEQ = EQ + 1;
        public final static int LT = LTEQ + 1;
    }

    public final static String NUM_SYMBOL_GTEQ = ">=";
    public final static String NUM_SYMBOL_GT = ">";
    public final static String NUM_SYMBOL_EQ = "==";
    public final static String NUM_SYMBOL_LTEQ = "<=";
    public final static String NUM_SYMBOL_LT = "<";

    public final static String NUM_SYMBOL_GTEQ_KO = "이상";
    public final static String NUM_SYMBOL_GT_KO = "초과";
    public final static String NUM_SYMBOL_EQ_KO = "";
    public final static String NUM_SYMBOL_LTEQ_KO = "이하";
    public final static String NUM_SYMBOL_LT_KO = "미만";

    private BigDecimal fromNumber  = null;
    private String fromSymbol = null;
    private int fromSymbolType = SymbolType.UNKNOWN;

    private BigDecimal toNumber  = null;
    private String toSymbol = null;
    private int toSymbolType = SymbolType.UNKNOWN;

    public String getFromNumber() {
        return this.fromNumber.toString();
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = new BigDecimal(fromNumber);
    }

    public NumberRange setFromNumber(String number, String rangeSymbol) {
        this.fromNumber = new BigDecimal(number);
        this.fromSymbol = rangeSymbol;
        this.fromSymbolType = toSymbolType(rangeSymbol);

        return this;
    }

    public String getFromSymbol() {
        return this.fromSymbol;
    }

    public void setFromSymbol(String fromSymbol) {
        this.fromSymbol = fromSymbol;
    }

    public String getToNumber() {
        return this.toNumber.toString();
    }

    public void setToNumber(String toNumber) {
        this.toNumber = new BigDecimal(toNumber);
    }

    public NumberRange setToNumber(String number, String rangeSymbol) {
        this.toNumber = new BigDecimal(number);
        this.toSymbol = rangeSymbol;
        this.toSymbolType = toSymbolType(rangeSymbol);

        return this;
    }

    public String getToSymbol() {
        return this.toSymbol;
    }

    public void setToSymbol(String toSymbol) {
        this.toSymbol = toSymbol;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        if (this.fromNumber != null) {
            sb.append(this.fromNumber).append(this.toSymbolStringKo(true));
        }

        if (this.fromNumber != null && this.toNumber != null) {
            sb.append(" ");
        }

        if (this.toNumber != null) {
            sb.append(this.toNumber).append(this.toSymbolStringKo(false));
        }
        return sb.toString();
    }

    public String toString(boolean isFrom) {
        StringBuilder sb = new StringBuilder();
        if (isFrom) {
            sb.append(this.fromNumber).append(this.toSymbolStringKo(true));
        } else {
            sb.append(this.toNumber).append(this.toSymbolStringKo(false));
        }

        return sb.toString();
    }

    public String toRawNumberString(boolean isFrom) {
        String number = null;
        String symbol = null;

        if (isFrom) {
            number = this.fromNumber.toString();
            symbol = this.fromSymbol;
        } else {
            number = this.toNumber.toString();
            symbol = this.toSymbol;
        }

        if (number == null && symbol == null) {
            return "";
        }

        return symbol + number;
    }

    public NumberRange fromString(String numberString) {
        String[] splits = numberString.split("\\:");

        if (splits.length == 0) {
            return null;
        }

        this.fromSymbol = splits[0].replaceAll("[-?0-9?.]+", "");
        this.fromSymbolType = toSymbolType(this.fromSymbol);
        this.fromNumber = new BigDecimal(splits[0].replaceAll("[^-?0-9?.]+", ""));

        if (splits.length > 1) {
            this.toSymbol = splits[1].replaceAll("[-?0-9?.]+", "");
            this.toSymbolType = toSymbolType(this.toSymbol);
            this.toNumber = new BigDecimal(splits[1].replaceAll("[^-?0-9?.]+", ""));
        } else {
            this.toSymbol = null;
            this.toSymbolType = toSymbolType(this.toSymbol);
            this.toNumber = null;
        }

//        JLog.logd("this.fromSymbol : " + this.fromSymbol);
//        JLog.logd("this.fromNumber : " + this.fromNumber);
//        JLog.logd("this.toSymbol : " + this.toSymbol);
//        JLog.logd("this.toNumber : " + this.toNumber);

        return this;
    }

    public boolean isInRange(String valueString) {
        BigDecimal value = new BigDecimal(valueString);

        if (isInFrom(value) == false) {
            return false;
        }

        if (isInTo(value) == false) {
            return false;
        }

        return true;
    }

    private boolean isInFrom(BigDecimal value) {
        if (this.fromNumber == null || value == null) {
            return false;
        }

        int symbolType = this.fromSymbolType;

        int compVal = value.compareTo(this.fromNumber);

//        JLog.logd("Symbol : " + toSymbol(symbolType));
//        JLog.logd("isInFrom compVal : " + compVal);

        if (symbolType == SymbolType.GTEQ
                && compVal >= 0) {
            return true;
        } else if (symbolType == SymbolType.GT
                && compVal > 0) {
            return true;
        } else if (symbolType == SymbolType.EQ
                && compVal == 0) {
            return true;
        } else if (symbolType == SymbolType.LTEQ
                && compVal <= 0) {
            return true;
        } else if (symbolType == SymbolType.LT
                && compVal < 0) {
            return true;
        }

        return false;
    }

    private boolean isInTo(BigDecimal value) {
        if (value == null) {
            return false;
        }

        if (this.toNumber == null) {
            return true;
        }

        int symbolType = this.toSymbolType;

        int compVal = value.compareTo(this.toNumber);

//        JLog.logd("Symbol : " + toSymbol(symbolType));
//        JLog.logd("isInTo compVal : " + compVal);

        if (symbolType == SymbolType.GTEQ
                && compVal >= 0) {
            return true;
        } else if (symbolType == SymbolType.GT
                && compVal > 0) {
            return true;
        } else if (symbolType == SymbolType.EQ
                && compVal == 0) {
            return true;
        } else if (symbolType == SymbolType.LTEQ
                && compVal <= 0) {
            return true;
        } else if (symbolType == SymbolType.LT
                && compVal < 0) {
            return true;
        }

        return false;
    }

    public String toSymbolStringKo(boolean isFrom) {

        if (isFrom) {
            if (NUM_SYMBOL_GTEQ.equals(this.fromSymbol)) {
                return NUM_SYMBOL_GTEQ_KO;
            } else if (NUM_SYMBOL_GT.equals(this.fromSymbol)) {
                return NUM_SYMBOL_GT_KO;
            } else if (NUM_SYMBOL_LTEQ.equals(this.fromSymbol)) {
                return NUM_SYMBOL_LTEQ_KO;
            } else if (NUM_SYMBOL_LT.equals(this.fromSymbol)) {
                return NUM_SYMBOL_LT_KO;
            }
        } else {
            if (NUM_SYMBOL_GTEQ.equals(this.toSymbol)) {
                return NUM_SYMBOL_GTEQ_KO;
            } else if (NUM_SYMBOL_GT.equals(this.toSymbol)) {
                return NUM_SYMBOL_GT_KO;
            } else if (NUM_SYMBOL_LTEQ.equals(this.toSymbol)) {
                return NUM_SYMBOL_LTEQ_KO;
            } else if (NUM_SYMBOL_LT.equals(this.toSymbol)) {
                return NUM_SYMBOL_LT_KO;
            }
        }

        return null;
    }

    private static String toSymbol(int symbolType) {
        String symbol = null;
        switch(symbolType) {
            case SymbolType.GTEQ:
                symbol = NUM_SYMBOL_GTEQ;
                break;
            case SymbolType.GT:
                symbol = NUM_SYMBOL_GT;
                break;
            case SymbolType.EQ:
                symbol = NUM_SYMBOL_EQ;
                break;
            case SymbolType.LTEQ:
                symbol = NUM_SYMBOL_LTEQ;
                break;
            case SymbolType.LT:
                symbol = NUM_SYMBOL_LT;
                break;
        }

        return symbol;
    }

    private static int toSymbolType(String symbol) {

        if (NUM_SYMBOL_GTEQ.equals(symbol)) {
            return SymbolType.GTEQ;
        } else if (NUM_SYMBOL_GT.equals(symbol)) {
            return SymbolType.GT;
        } else if (NUM_SYMBOL_EQ.equals(symbol)) {
            return SymbolType.EQ;
        } else if (NUM_SYMBOL_LTEQ.equals(symbol)) {
            return SymbolType.LTEQ;
        } else if (NUM_SYMBOL_LT.equals(symbol)) {
            return SymbolType.LT;
        }

        return SymbolType.UNKNOWN;
    }
}
