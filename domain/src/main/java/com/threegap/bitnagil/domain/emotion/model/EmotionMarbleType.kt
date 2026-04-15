package com.threegap.bitnagil.domain.emotion.model

import kotlinx.serialization.Serializable

/**
 * 감정구슬 타입
 *
 * @property CALM 평온함
 * @property VITALITY 활기참
 * @property LETHARGY 무기력함
 * @property ANXIETY 활기참
 * @property SATISFACTION 만족함
 * @property FATIGUE 피곤함
 */
@Serializable
enum class EmotionMarbleType { CALM, VITALITY, LETHARGY, ANXIETY, SATISFACTION, FATIGUE }
