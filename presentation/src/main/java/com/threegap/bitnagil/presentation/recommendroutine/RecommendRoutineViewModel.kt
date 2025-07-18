package com.threegap.bitnagil.presentation.recommendroutine

import androidx.lifecycle.SavedStateHandle
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.recommendroutine.model.RecommendRoutine
import com.threegap.bitnagil.presentation.recommendroutine.model.RecommendRoutineIntent
import com.threegap.bitnagil.presentation.recommendroutine.model.RecommendRoutineSideEffect
import com.threegap.bitnagil.presentation.recommendroutine.model.RecommendRoutineState
import com.threegap.bitnagil.presentation.recommendroutine.type.RecommendRoutineCategory
import com.threegap.bitnagil.presentation.recommendroutine.type.RecommendRoutineDifficulty
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class RecommendRoutineViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : MviViewModel<RecommendRoutineState, RecommendRoutineSideEffect, RecommendRoutineIntent>(
    initState = RecommendRoutineState(),
    savedStateHandle = savedStateHandle,
) {

    init {
        sendIntent(RecommendRoutineIntent.LoadRecommendRoutines)
    }

    override suspend fun SimpleSyntax<RecommendRoutineState, RecommendRoutineSideEffect>.reduceState(
        intent: RecommendRoutineIntent,
        state: RecommendRoutineState,
    ): RecommendRoutineState? = when (intent) {
        is RecommendRoutineIntent.LoadRecommendRoutines -> {
            val dummyRecommendRoutines = generateDummyRecommendRoutine()
            state.copy(
                isLoading = false,
                recommendRoutines = dummyRecommendRoutines,
            )
        }

        is RecommendRoutineIntent.OnCategorySelected -> state.copy(
            selectedCategory = intent.category,
        )

        is RecommendRoutineIntent.ShowDifficultyBottomSheet -> {
            state.copy(showDifficultyBottomSheet = true)
        }

        is RecommendRoutineIntent.HideDifficultyBottomSheet -> {
            state.copy(showDifficultyBottomSheet = false)
        }

        is RecommendRoutineIntent.OnDifficultySelected -> state.copy(
            selectedDifficulty = intent.difficulty,
            showDifficultyBottomSheet = false,
        )

        RecommendRoutineIntent.ClearDifficultyFilter -> state.copy(
            selectedDifficulty = null,
        )
    }

    private fun generateDummyRecommendRoutine(): Map<RecommendRoutineCategory, List<RecommendRoutine>> {
        return mapOf(
            RecommendRoutineCategory.CUSTOM_RECOMMEND to emptyList(),
            RecommendRoutineCategory.GO_OUT to listOf(
                RecommendRoutine("나가는 루틴", "밖으로 나가보자고", RecommendRoutineDifficulty.EASY),
                RecommendRoutine("나가는 루틴22", "밖으로 나가보자고", RecommendRoutineDifficulty.NORMAL),
                RecommendRoutine("나가는 루틴33", "밖으로 나가보자고", RecommendRoutineDifficulty.HARD),
                RecommendRoutine("나가는 루틴44", "밖으로 나가보자고", RecommendRoutineDifficulty.EASY),
                RecommendRoutine("나가는 루틴55", "밖으로 나가보자고", RecommendRoutineDifficulty.NORMAL),
                RecommendRoutine("나가는 루틴66", "밖으로 나가보자고", RecommendRoutineDifficulty.HARD),
                RecommendRoutine("나가는 루틴77", "밖으로 나가보자고", RecommendRoutineDifficulty.EASY),
                RecommendRoutine("나가는 루틴88", "밖으로 나가보자고", RecommendRoutineDifficulty.NORMAL),
            ),
            RecommendRoutineCategory.WAKE_UP to emptyList(),
            RecommendRoutineCategory.CONNECT to listOf(
                RecommendRoutine("연결해요 루틴", "연결하는 루틴임", RecommendRoutineDifficulty.EASY),
                RecommendRoutine("연결해요 루틴22", "연결하는 루틴임22", RecommendRoutineDifficulty.NORMAL),
                RecommendRoutine("연결해요 루틴33", "연결하는 루틴임33", RecommendRoutineDifficulty.HARD),
            ),
            RecommendRoutineCategory.TAKE_REST to listOf(
                RecommendRoutine("쉬어가요 루틴", "쉬어가요 루틴임", RecommendRoutineDifficulty.EASY),
                RecommendRoutine("쉬어가요 루틴22", "쉬어가요 루틴임22", RecommendRoutineDifficulty.EASY),
                RecommendRoutine("쉬어가요 루틴33", "쉬어가요 루틴임33", RecommendRoutineDifficulty.NORMAL),
                RecommendRoutine("쉬어가요 루틴44", "쉬어가요 루틴임44", RecommendRoutineDifficulty.HARD),
            ),
            RecommendRoutineCategory.GROW to listOf(
                RecommendRoutine("성장해요 루틴", "성장해요 루틴임", RecommendRoutineDifficulty.NORMAL),
                RecommendRoutine("성장해요 루틴22", "성장해요 루틴임22", RecommendRoutineDifficulty.HARD),
                RecommendRoutine("성장해요 루틴33", "성장해요 루틴임33", RecommendRoutineDifficulty.NORMAL),
                RecommendRoutine("성장해요 루틴44", "성장해요 루틴임44", RecommendRoutineDifficulty.HARD),
            ),
        )
    }
}
