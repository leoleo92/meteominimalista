package com.leonardo.pani.weatherapp.view.main

import com.leonardo.pani.weatherapp.model.jsonGenerated.*

class Utils {


     companion object{
         val previewApiResponse = DaysForecasts(daily= DailyX(precipitation_hours= listOf(1.0, 5.0, 19.0, 2.0, 0.0, 0.0, 6.0), precipitation_sum= listOf(0.1, 41.5, 22.7, 2.2, 0.0, 0.0, 2.1), sunrise= listOf("2022-08-16T06:23", "2022-08-17T06:24", "2022-08-18T06:26", "2022-08-19T06:27", "2022-08-20T06:28", "2022-08-21T06:29", "2022-08-22T06:30"),
             sunset= listOf("2022-08-16T20:31", "2022-08-17T20:29", "2022-08-18T20:28", "2022-08-19T20:26", "2022-08-20T20:24", "2022-08-21T20:22", "2022-08-22T20:21"),
             temperature_2m_max= listOf(30.9, 29.2, 23.1, 26.5, 31.9, 30.0, 27.3),
             temperature_2m_min= listOf(19.3, 21.5, 19.9, 19.1, 18.4, 20.2, 21.6),
             time= listOf("2022-08-16", "2022-08-17", "2022-08-18", "2022-08-19", "2022-08-20", "2022-08-21", "2022-08-22"),
             weathercode= listOf(80, 99, 95, 95, 1, 3, 80)),
             daily_units= DailyUnits(precipitation_hours="h", precipitation_sum="mm", sunrise="iso8601", sunset="iso8601", temperature_2m_max="° C",
                 temperature_2m_min="°C", time="iso8601", weathercode="wmo code"), elevation=139.0, generationtime_ms=0.7139444351196289,
             hourly= HourlyX(apparent_temperature= listOf(21.8, 22.2, 21.7, 21.6, 21.9, 21.7, 21.4, 21.4, 22.7, 24.3, 25.6, 27.1,
                 29.6, 31.1, 31.7, 33.5, 33.2, 32.6, 32.5, 32.0, 30.7, 30.2, 29.1, 28.2, 27.0, 26.1, 26.4, 25.3, 25.1, 24.4, 24.4,
                 24.3, 24.8, 26.0, 27.3, 29.1, 31.0, 31.9, 31.9, 31.1, 26.0, 25.2, 27.4, 27.3, 26.0, 25.9, 26.1, 25.7, 25.3, 24.6,
                 24.3, 23.9, 23.4, 23.1, 23.6, 23.6, 23.5, 22.9, 23.6, 24.0, 24.3, 24.5, 24.9, 24.0, 24.7, 24.3, 24.0, 23.2, 23.3,
                 22.7, 22.0, 22.4, 21.9, 22.5, 22.2, 22.2, 21.8, 21.4, 21.2, 21.1, 21.3, 22.0, 23.0, 24.3, 25.0, 25.7, 26.4, 26.8,
                 27.3, 27.5, 27.5, 27.4, 26.8, 26.1, 25.2, 24.0, 23.1, 22.1, 21.1, 20.7, 20.5, 20.2, 20.0, 19.6, 20.2, 21.9, 24.2,
                 27.0, 29.5, 31.3, 32.7, 33.2, 32.8, 32.2, 31.6, 31.1, 30.3, 28.6, 27.4, 25.6, 24.5, 23.7, 22.7, 22.2, 21.8, 21.6,
                 21.6, 21.8, 22.6, 24.0, 25.8, 28.0, 29.2, 30.8, 32.2, 32.6, 32.4, 31.7, 31.3, 30.8, 29.9, 28.8, 27.7, 26.3, 25.4, 24.6, 23.8, 23.4, 23.0, 22.8, 23.0, 23.3, 23.9, 24.4, 25.0,
                 25.9, 26.8, 27.7, 28.7, 28.9, 29.0, 28.9, 28.8, 28.6, 28.1, 27.7, 26.8, 25.7),
                 precipitation= listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                     0.1, 0.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 40.2, 0.9, 0.0, 0.1, 0.2, 0.0, 0.0, 0.0, 0.0, 0.0,
                     0.0, 0.3, 0.5, 4.4, 0.1, 0.0, 0.4, 0.1, 0.5, 0.9, 0.4, 0.2, 0.1, 1.5, 0.7, 2.3, 3.5, 0.0, 1.0, 3.8, 1.5, 0.5, 2.1, 0.1, 0.0, 0.0, 0.0, 0.0,
                     0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                     0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                     0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.4, 0.4, 0.4, 0.3, 0.3, 0.3, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                     0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                 temperature_2m= listOf(20.3, 20.1, 19.9, 19.8, 20.0, 19.8, 19.5, 19.3, 20.4, 22.1, 23.7, 25.2, 26.8, 28.2, 29.0, 30.1, 30.6, 30.8, 30.9, 30.4, 29.3,
                     27.9, 26.5, 25.6, 24.6, 24.0, 23.8, 23.3, 22.8, 22.1, 21.6, 21.5, 22.3, 23.2, 25.0, 26.4, 27.9, 28.7, 29.2, 28.9, 22.0, 24.0, 24.7, 24.7, 23.9,
                     22.5, 22.4, 22.2, 22.0, 21.6, 21.4, 21.2, 21.0, 20.5, 20.5, 20.4, 20.6, 20.9, 21.3, 21.4, 22.2, 22.5, 23.1, 21.7, 21.6, 21.4, 21.1, 21.0, 20.5,
                     20.1, 20.0, 19.9, 19.6, 19.7, 19.5, 19.4, 19.3, 19.2, 19.2, 19.1, 19.4, 20.2, 21.4, 22.8, 23.7, 24.4, 25.3, 25.8, 26.3, 26.5, 26.4, 26.2, 25.5,
                     24.6, 23.5, 22.2, 21.5, 20.8, 20.1, 19.6, 19.3, 18.9, 18.6, 18.4, 18.9, 20.4, 22.5, 25.3, 26.9, 28.4, 30.0, 30.9, 31.6, 31.9, 31.3, 30.2, 29.0,
                     26.8, 25.3, 23.6, 22.8, 22.1, 21.4, 20.9, 20.5, 20.3, 20.2, 20.2, 20.9, 22.1, 23.8, 25.8, 27.0, 27.9, 28.9, 29.5, 29.9, 30.0, 29.5, 28.7, 27.4,
                     26.4, 25.4, 24.1, 23.4, 22.9, 22.4, 22.0, 21.8, 21.6, 21.6, 21.6, 21.8, 22.3, 22.9, 23.8, 24.6, 25.6, 26.7, 27.1, 27.3, 27.3, 27.1, 26.6, 25.8,
                     25.1, 24.3, 23.3),
                 time= listOf("2022-08-16T00:00", "2022-08-16T01:00", "2022-08-16T02:00", "2022-08-16T03:00", "2022-08-16T04:00", "2022-08-16T05:00", "2022-08-16T06:00",
                     "2022-08-16T07:00", "2022-08-16T08:00", "2022-08-16T09:00", "2022-08-16T10:00", "2022-08-16T"), weathercode = listOf(80, 99, 95, 95, 1, 3, 80)), hourly_units = HourlyUnits("29","0","30","2022-08-16","0"), latitude = 0.0,longitude=0.0, utc_offset_seconds = 0)



         val detailedApiReponse = WeatherForecast( current=Current(clouds=63, dew_point=14.34, dt=1660602117,
             feels_like=22.63, humidity=59, pressure=1010, sunrise=1660618202, sunset=1660662911, temp=22.76, uvi=0.0, visibility=10000,
             weather= listOf(Weather(description="nubi sparse", icon="04n", id=803, main="Clouds")),
             wind_deg=215, wind_gust=13.41, wind_speed=7.4), daily= listOf(Daily(clouds=30, dew_point=13.2, dt=1660640400,
             feels_like=FeelsLike(day=29.42, eve=26.89, morn=21.16, night=24.4), humidity=35, moon_phase=0.66,
             moonrise=1660675920, moonset=1660631820, pop=0.1, pressure=1009, sunrise=1660618202, sunset=1660662911,
             temp=Temp(day=30.24, eve=26.71, max=31.14, min=21.33, morn=21.33, night=24.58), uvi=12.7,
             weather= listOf(WeatherX(description="nubi sparse", icon="03d", id=802, main="Clouds")),
             wind_deg=215, wind_gust=14.24, wind_speed=11.55),
             Daily(clouds=24, dew_point=13.24, dt=1660726800, feels_like=FeelsLike(day=29.15, eve=27.53, morn=21.3, night=25.42),
                 humidity=35, moon_phase=0.69, moonrise=1660764780, moonset=1660721160, pop=0.32, pressure=1009, sunrise=1660704604,
                 sunset=1660749286, temp=Temp(day=29.98, eve=28.07, max=30.69, min=21.41, morn=21.41, night=25.7), uvi=11.68,
                 weather= listOf(WeatherX(description="pioggia leggera", icon="10d", id=500, main="Rain")),
                 wind_deg=225, wind_gust=13.16, wind_speed=11.69), Daily(clouds=100, dew_point=11.88, dt=1660813200,
                 feels_like=FeelsLike(day=28.22, eve=27.68, morn=21.11, night=26.14), humidity=34, moon_phase=0.72,
                 moonrise=1660853700, moonset=1660810500, pop=0.0, pressure=1009, sunrise=1660791005, sunset=1660835660,
                 temp=Temp(day=29.1, eve=28.7, max=30.82, min=21.52, morn=21.52, night=26.14), uvi=11.83,
                 weather= listOf(WeatherX(description="cielo coperto", icon="04d", id=804, main="Clouds")),
                 wind_deg=225, wind_gust=15.0, wind_speed=12.45), Daily(clouds=18, dew_point=10.21, dt=1660899600,
                 feels_like=FeelsLike(day=28.88, eve=28.21, morn=21.02, night=26.5), humidity=28, moon_phase=0.75,
                 moonrise=1660942740, moonset=1660899900, pop=0.0, pressure=1010, sunrise=1660877405, sunset=1660922034,
                 temp=Temp(day=30.29, eve=29.32, max=31.99, min=21.56, morn=21.56, night=26.5), uvi=12.22,
                 weather= listOf(WeatherX(description="poche nuvole", icon="02d", id=801, main="Clouds")),
                 wind_deg=219, wind_gust=15.59, wind_speed=12.01), Daily(clouds=52, dew_point=11.54, dt=1660986000,
                 feels_like=FeelsLike(day=29.49, eve=29.02, morn=20.55, night=26.68), humidity=30, moon_phase=0.79, moonrise=0,
                 moonset=1660989300, pop=0.0, pressure=1010, sunrise=1660963806, sunset=1661008407, temp=Temp(day=30.76, eve=30.65,
                     max=33.02, min=21.03, morn=21.03, night=27.14), uvi=12.58, weather= listOf(
                     WeatherX(description="nubi sparse", icon="04d", id=803, main="Clouds")),
                 wind_deg=217, wind_gust=15.65, wind_speed=11.08), Daily(clouds=96, dew_point=12.87, dt=1661072400,
                 feels_like=FeelsLike(day=28.47, eve=26.77, morn=20.88, night=23.79), humidity=36, moon_phase=0.82,
                 moonrise=1661031960, moonset=1661078820, pop=0.0, pressure=1011, sunrise=1661050205, sunset=1661094779,
                 temp=Temp(day=29.21, eve=26.68, max=29.75, min=20.86, morn=20.86, night=23.98), uvi=13.0,
                 weather= listOf(WeatherX(description="cielo coperto", icon="04d", id=804, main="Clouds")),
                 wind_deg=219, wind_gust=14.79, wind_speed=11.71), Daily(clouds=23, dew_point=12.26, dt=1661158800,
                 feels_like=FeelsLike(day=28.83, eve=28.65, morn=20.25, night=26.55), humidity=33, moon_phase=0.85,
                 moonrise=1661121300, moonset=1661168280, pop=0.03, pressure=1011, sunrise=1661136605, sunset=1661181151,
                 temp=Temp(day=29.84, eve=29.81, max=32.28, min=20.5, morn=20.5, night=26.55), uvi=13.0,
                 weather= listOf(WeatherX(description="poche nuvole", icon="02d", id=801, main="Clouds")),
                 wind_deg=234, wind_gust=15.41, wind_speed=12.4), Daily(clouds=0, dew_point=10.88, dt=1661245200,
                 feels_like=FeelsLike(day=29.18, eve=28.83, morn=19.79, night=26.41), humidity=29, moon_phase=0.88,
                 moonrise=1661210700, moonset=1661257740, pop=0.39, pressure=1009, sunrise=1661223004, sunset=1661267522,
                 temp=Temp(day=30.53, eve=30.08, max=33.04, min=20.51, morn=20.51, night=26.41), uvi=13.0,
                 weather= listOf(WeatherX(description="pioggia leggera", icon="10d", id=5, main = "Clouds")), wind_deg = 2, wind_gust = 1.0
             , wind_speed = 2.1)), cityName = "Milan", coordinates = listOf(9.18592,45.465421),
         daysForecast = emptyList(), hourly = emptyList(), lat =45.465421, lon =  9.18592, timezone = "", timezone_offset = 0)

     }
}