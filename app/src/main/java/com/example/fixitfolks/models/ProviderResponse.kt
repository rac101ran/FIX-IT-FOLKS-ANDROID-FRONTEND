package com.example.fixitfolks.models

data class ProviderResponse(
    val status: String,
    val data: ProvidersData
)

data class Providers(
    val provider: Provider,
    val services: List<String>
)

data class ProvidersData(
    val providers: List<Providers>
)

//................

data class ProviderResponseItem(
    val status: String,
    val data: ProvidersItemData
)

data class ProvidersItemData(
    val providers: ProviderItem,
)
data class ProviderItem(
    val services: List<Services>,
    val provider: List<Provider>
)

//................