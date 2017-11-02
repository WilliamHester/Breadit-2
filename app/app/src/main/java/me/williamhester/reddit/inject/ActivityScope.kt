package me.williamhester.reddit.inject

import javax.inject.Scope

/** Annotation for Activity-scoped bindings */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope
