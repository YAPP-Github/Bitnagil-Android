package com.threegap.bitnagil.domain.activitylog.model

import kotlinx.serialization.Serializable

/**
 * 활동 뱃지 타입
 *
 * @property MOTIVATION_EXPERT 의욕 전문가 - 그 달 감정 구슬 선택 3회
 * @property CHECK_EXPERT 체크 전문가 - 그 달 루틴(메인) 완료 1회
 * @property OUTING_EXPERT 외출 전문가 - 그 달 제보 등록 1회
 * @property RESERVE_EXPERT 예비 전문가 - 그 달 획득 뱃지가 0개일 때 기본 표시
 */
@Serializable
enum class BadgeType { MOTIVATION_EXPERT, CHECK_EXPERT, OUTING_EXPERT, RESERVE_EXPERT }
