package com.harry.make_and_model_repository.model

import com.harry.make_and_model_repository.MakeAndModelRepository

/**
 * Mock Repository for retrieving makes and model information
 *
 * This would be swapped out later, probably would change
 * to suspend functions depending on data source.
 */
internal class MakeAndModelRepositoryImpl : MakeAndModelRepository {

    // Mock information, could fetch this info from api
    override fun getMakes(): List<Make> {
        return listOf(
            "Abarth",
            "Audi",
            "Bentley",
            "BMW",
            "Bugatti",
            "Cadillac",
            "Chevrolet",
            "Chrysler",
            "Citroën",
            "Dacia",
            "Daewoo",
            "Daihatsu",
            "Dodge",
            "Donkervoort",
            "DS",
            "Ferrari",
            "Fiat",
            "Fisker",
            "Ford",
            "Honda",
            "Hummer",
            "Hyundai",
            "Infiniti",
            "Iveco",
            "Jaguar",
            "Jeep",
            "Kia",
            "KTM",
            "Lada",
            "Lamborghini",
            "Lancia",
            "Land Rover",
            "Landwind",
            "Lexus",
            "Lotus",
            "Maserati",
            "Maybach",
            "Mazda",
            "McLaren",
            "Mercedes-Benz",
            "MG",
            "Mini",
            "Mitsubishi",
            "Morgan",
            "Nissan",
            "Opel",
            "Peugeot",
            "Porsche",
            "Renault",
            "Rolls-Royce",
            "Rover",
            "Saab",
            "Seat",
            "Skoda",
            "Smart",
            "SsangYong",
            "Subaru",
            "Suzuki",
            "Tesla",
            "Toyota",
            "Volkswagen",
            "Volvo"
        ).mapIndexed { index, name ->
            if (index % 2 == 0) {
                Make(name = name, models = listOf("Broom", "Fastest", "Sandero", "Cooper"))
            } else {
                Make(name = name, models = listOf("X1", "Zebra", "Oxtail", "Panther"))
            }
        }
    }

    // Mock information, could fetch this info from api
    override fun getYearsByModel(model: String): List<String> {
        return listOf(
            "2022",
            "2021",
            "2020",
            "2019",
            "2018",
            "2017",
            "2016",
            "2015",
            "2014",
            "2013",
            "2012",
            "2011",
            "2010",
            "2009",
            "2008",
            "2007",
            "2006",
            "2005"
        )
    }
}
