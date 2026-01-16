package com.thejohnsondev.artseum.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

private const val DEFAULT_TRANSITION_DURATION = 500

fun AnimatedContentTransitionScope<NavBackStackEntry>.getDefaultEnterTransition() =
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = tween(DEFAULT_TRANSITION_DURATION)
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.getDefaultExitTransition() =
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = tween(DEFAULT_TRANSITION_DURATION)
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.getDefaultPopEnterTransition() =
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.End,
        animationSpec = tween(DEFAULT_TRANSITION_DURATION)
    ) + fadeIn(tween(DEFAULT_TRANSITION_DURATION))

fun AnimatedContentTransitionScope<NavBackStackEntry>.getDefaultPopExitTransition() =
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.End,
        animationSpec = tween(DEFAULT_TRANSITION_DURATION)
    )

inline fun <reified T : Any> NavGraphBuilder.composableAnimated(
    noinline content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit),
) {
    composable<T>(
        enterTransition = {
            getDefaultEnterTransition()
        },
        exitTransition = {
            getDefaultExitTransition()
        },
        popEnterTransition = {
            getDefaultPopEnterTransition()
        },
        popExitTransition = {
            getDefaultPopExitTransition()
        },
    ) {
        content(it)
    }
}