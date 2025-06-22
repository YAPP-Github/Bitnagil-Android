package com.threegap.bitnagil.presentation.common.mviviewmodel

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.parcelize.Parcelize
import org.junit.Before
import org.junit.Test
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.test.test

@ExperimentalCoroutinesApi
class MviViewModelTest {
    private lateinit var sampleMviViewModel: SampleMviViewModel

    @Before
    fun setUp() {
        sampleMviViewModel = SampleMviViewModel(initState = SampleState(), savedStateHandle = SavedStateHandle())
    }

    @Test
    fun `state는 호출 순서대로 갱신되어야 한다`() =
        runTest {
            sampleMviViewModel.test(testScope = this) {
                containerHost.sendIntent(SampleIntent.Increase(number = 1))
                containerHost.sendIntent(SampleIntent.Decrease(number = 2))
                containerHost.sendIntent(SampleIntent.Increase(number = 3))

                expectState { SampleState() }
                expectState { SampleState(count = 1) }
                expectState { SampleState(count = -1) }
                expectState { SampleState(count = 2) }
            }
        }

    @Test
    fun `state와 sideEffect는 호출 순서대로 갱신되어야 한다`() =
        runTest {
            sampleMviViewModel.test(testScope = this) {
                containerHost.sendIntent(SampleIntent.Increase(number = 1))
                containerHost.sendIntent(SampleIntent.Clear)
                containerHost.sendIntent(SampleIntent.Decrease(number = 2))
                containerHost.sendIntent(SampleIntent.Increase(number = 3))

                expectState { SampleState() }
                expectState { SampleState(count = 1) }
                expectSideEffect(SampleSideEffect.ShowToast("Clear"))
                expectState { SampleState() }
                expectState { SampleState(count = -2) }
                expectState { SampleState(count = 1) }
            }
        }

    // only for test
    private class SampleMviViewModel(
        initState: SampleState,
        savedStateHandle: SavedStateHandle,
    ) : MviViewModel<SampleState, SampleSideEffect, SampleIntent>(initState, savedStateHandle) {
        override suspend fun SimpleSyntax<SampleState, SampleSideEffect>.reduceState(
            intent: SampleIntent,
            state: SampleState,
        ): SampleState? {
            val newState =
                when (intent) {
                    is SampleIntent.Decrease -> state.copy(count = state.count - intent.number)
                    is SampleIntent.Increase -> state.copy(count = state.count + intent.number)
                    SampleIntent.Clear -> {
                        sendSideEffect(sideEffect = SampleSideEffect.ShowToast("Clear"))
                        state.copy(count = 0)
                    }
                }
            return newState
        }
    }

    @Parcelize
    private data class SampleState(
        val count: Int = 0,
    ) : MviState

    private sealed class SampleSideEffect : MviSideEffect {
        data class ShowToast(val message: String) : SampleSideEffect()
    }

    private sealed class SampleIntent : MviIntent {
        data class Increase(val number: Int) : SampleIntent()

        data class Decrease(val number: Int) : SampleIntent()

        object Clear : SampleIntent()
    }
}
