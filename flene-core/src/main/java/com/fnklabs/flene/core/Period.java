package com.fnklabs.flene.core;

import org.joda.time.DateTime;

import java.util.function.Function;

public enum Period {
    MINUTE(date -> date.withMillisOfSecond(0).withSecondOfMinute(0)),
    MINUTES_5(dateTime -> dateTime.withMillisOfSecond(0).withSecondOfMinute(0).withMinuteOfHour(dateTime.getMinuteOfHour() % 5 * 5)),
    MINUTES_15(dateTime -> dateTime.withMillisOfSecond(0).withSecondOfMinute(0).withMinuteOfHour(dateTime.getMinuteOfHour() % 15 * 15)),
    MINUTES_30(dateTime -> dateTime.withMillisOfSecond(0).withSecondOfMinute(0).withMinuteOfHour(dateTime.getMinuteOfHour() % 30 * 30)),
    HOUR(dateTime -> dateTime.withMillisOfSecond(0).withSecondOfMinute(0).withMinuteOfHour(0)),
    DAY(dateTime -> dateTime.withMillisOfSecond(0).withSecondOfMinute(0).withMinuteOfHour(0).withHourOfDay(0)),
    MONTH(dateTime -> dateTime.withMillisOfSecond(0).withSecondOfMinute(0).withMinuteOfHour(0).withHourOfDay(0).withDayOfMonth(0)),;

    private final Function<DateTime, DateTime> converter;

    Period(Function<DateTime, DateTime> converter) {
        this.converter = converter;
    }

    public DateTime getStartOfPeriod(DateTime date) {
        return converter.apply(date);
    }
}
