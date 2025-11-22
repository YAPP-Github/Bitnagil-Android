package com.threegap.bitnagil.data.address.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Coord2AddressResponse(
    @SerialName("meta")
    val meta: Meta,
    @SerialName("documents")
    val documents: List<Document>,
)

@Serializable
data class Meta(
    @SerialName("total_count")
    val totalCount: Int,
)

@Serializable
data class Document(
    @SerialName("road_address")
    val roadAddress: RoadAddress,
    @SerialName("address")
    val address: Address,
)

@Serializable
data class Address(
    @SerialName("address_name")
    val addressName: String,
    @SerialName("region_1depth_name")
    val region1depthName: String,
    @SerialName("region_2depth_name")
    val region2depthName: String,
    @SerialName("region_3depth_name")
    val region3depthName: String,
    @SerialName("mountain_yn")
    val mountainYn: String,
    @SerialName("main_address_no")
    val mainAddressNo: String,
    @SerialName("sub_address_no")
    val subAddressNo: String,
)

@Serializable
data class RoadAddress(
    @SerialName("address_name")
    val addressName: String,
    @SerialName("region_1depth_name")
    val region1depthName: String,
    @SerialName("region_2depth_name")
    val region2depthName: String,
    @SerialName("region_3depth_name")
    val region3depthName: String,
    @SerialName("road_name")
    val roadName: String,
    @SerialName("underground_yn")
    val undergroundYn: String,
    @SerialName("main_building_no")
    val mainBuildingNo: String,
    @SerialName("sub_building_no")
    val subBuildingNo: String,
    @SerialName("building_name")
    val buildingName: String,
    @SerialName("zone_no")
    val zoneNo: String,
)

fun Coord2AddressResponse.toAddress(): String? {
    return this.documents
        .firstOrNull()
        ?.address
        ?.addressName
}
