create table if not exists active
(
    id                                bigint not null
        constraint active_pk
            primary key,
    latitude_deg                      numeric,
    longitude_deg                     numeric,
    latitude_rad                      numeric,
    longitude_rad                     numeric,
    height                            numeric,
    norad                             bigint,
    epoch                             numeric,
    utc_datetime                      timestamp,
    drag_coefficient                  numeric,
    "ballistic coefficient"           numeric,
    second_derivative_mean_motion     numeric,
    argument_of_perigee               numeric,
    inclination                       numeric,
    mean_anomaly                      numeric,
    mean_motion                       numeric,
    right_ascension_of_ascending_node numeric,
    tle1                              text,
    tle2                              text
);

create table if not exists cosmos
(
    id                                bigint not null
        constraint cosmos_pk
            primary key,
    latitude_deg                      numeric,
    longitude_deg                     numeric,
    latitude_rad                      numeric,
    longitude_rad                     numeric,
    height                            numeric,
    norad                             bigint,
    epoch                             numeric,
    utc_datetime                      timestamp,
    drag_coefficient                  numeric,
    "ballistic coefficient"           numeric,
    second_derivative_mean_motion     numeric,
    argument_of_perigee               numeric,
    inclination                       numeric,
    mean_anomaly                      numeric,
    mean_motion                       numeric,
    right_ascension_of_ascending_node numeric,
    tle1                              text,
    tle2                              text
);

create table if not exists iridium
(
    id                                bigint not null
        constraint iridium_pk
            primary key,
    latitude_deg                      numeric,
    longitude_deg                     numeric,
    latitude_rad                      numeric,
    longitude_rad                     numeric,
    height                            numeric,
    norad                             bigint,
    epoch                             numeric,
    utc_datetime                      timestamp,
    drag_coefficient                  numeric,
    "ballistic coefficient"           numeric,
    second_derivative_mean_motion     numeric,
    argument_of_perigee               numeric,
    inclination                       numeric,
    mean_anomaly                      numeric,
    mean_motion                       numeric,
    right_ascension_of_ascending_node numeric,
    tle1                              text,
    tle2                              text
);
