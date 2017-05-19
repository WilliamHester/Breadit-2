package me.williamhester.reddit.inject;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * A scope for retaining things for the life of a context.
 *
 * This allows us to provide certain things only for the life of an Activity, and when that Activity
 * is finished, all of those things can be stopped/discarded. This is so that we will be able to do
 * things like cancel outgoing requests so that the EventBus-based architecture doesn't send an
 * event to a Fragment or an Activity after it has been finished.
 *
 * Notes: Activities should not share EventBuses or clients so that messages are not construed
 * between multiple Activities.
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope { }
