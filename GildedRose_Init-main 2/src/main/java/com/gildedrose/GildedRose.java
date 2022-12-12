package com.gildedrose;

import static java.lang.String.format;

class GildedRose {
    public static final int MAX_QUALITY = 50;
    public static final int MIN_QUALITY = 0;
    private final Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {

        for (Item item : items) {

            if (degradesNaturally(item)) {
                decrementQuality(item);
            } else if (getsBetterWithAge(item)) {
                incrementQuality(item);

                if (isBackstagePass(item)) {
                    if (tenDaysOrLess(item)) {
                        incrementQuality(item);
                    }

                    if (fiveDaysOrLess(item)) {
                        incrementQuality(item);
                    }
                }
            }

            if (pastSellByDate(item)) {

                if (isAgedBrie(item)) {
                    incrementQuality(item);
                } else {
                    decrementQuality(item);
                }

                if (isBackstagePass(item)) {
                    setQualityToZero(item);
                }

            }

            decreaseSellIn(item);
        }
    }

    private void incrementQuality(final Item item) {
        if (item.quality < MAX_QUALITY) {
            item.quality = item.quality + 1;
        }
    }

    private void decrementQuality(Item item) {
        if (item.quality > MIN_QUALITY) {
            if (!isSulfuras(item)) {
                item.quality = item.quality - 1;
            }

            if (isConjured(item)) {
                item.quality = item.quality - 1;
            }
        }
    }

    private void setQualityToZero(Item item) {
        item.quality = 0;
    }

    private void decreaseSellIn(final Item item) {
        if (!isSulfuras(item)) {
            item.sellIn = item.sellIn - 1;
        }
    }

    private boolean degradesNaturally(Item item) {
        return !getsBetterWithAge(item);
    }

    private boolean getsBetterWithAge(final Item item) {
        return isAgedBrie(item) || isBackstagePass(item);
    }

    private boolean pastSellByDate(final Item item) {
        return item.sellIn < 0;
    }

    private boolean fiveDaysOrLess(Item item) {
        return item.sellIn < 6;
    }

    private boolean tenDaysOrLess(Item item) {
        return item.sellIn < 11;
    }

    private boolean isAgedBrie(Item item) {
        return item.name.equals("Aged Brie");
    }

    private boolean isBackstagePass(Item item) {
        return item.name.equals("Backstage passes to a TAFKAL80ETC concert");
    }

    private boolean isSulfuras(Item item) {
        return item.name.equals("Sulfuras, Hand of Ragnaros");
    }

    private boolean isConjured(Item item) {
        return item.name.equals("Conjured");
    }

    public Item getItem(String name) {
        Item[] clone = items.clone();
        for (Item item : clone) {
            if (name.equalsIgnoreCase(item.name)) {
                return item;
            }
        }

        throw new RuntimeException(format("Item '%s' not found", name));
    }
}