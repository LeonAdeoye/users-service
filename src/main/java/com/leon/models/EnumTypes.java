package com.leon.models;

public final class EnumTypes
{
    public static enum RegionEnum
    {
        NONE(""), ASIA("Asia"), EUROPE("Europe"), AMERICAS("America"), AFRICA("Africa");

        private final String region;

        private RegionEnum(String region)
        {
            this.region = region;
        }

        public String getRegion()
        {
            return this.region;
        }
    }
}
