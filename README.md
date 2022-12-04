# Описание работы с Prometheus и Grafana
- Вся конфигурация лежит в /data
- Для Prometheus в /data/prometheus/config/prometheus.yaml лежат настройки:
> Приложение запускается на порте 8080, поэтому все /actuator/* берутся оттуда.
> Сам Prometheus поднимается на порту 9090, можно зайти и посмотреть
- Grafana поднимаетмя на порту 3000, дефолтно admin/admin на входе
- Если при деплое нужно портв поменять, то аккуратно во всех конфигах, чтобы соответствовали)

## Как это всё дело поднять:
- Сначала поднять приложение и убедиться запросом "curl http://localhost:8080/actuator", что всё живо.
- Также написан конфиг docker-compose.yaml, в котором поднимается Prometheus и Grafana.
- Можно запустить через docker compose up.

## Actuator
- http://localhost:8080/actuator
- http://localhost:8080/actuator/info
- http://localhost:8080/actuator/health
- http://localhost:8080/actuator/prometheus

## Prometheus 
- Запустится на http://localhost:9090 
- Prometheus jobs - http://localhost:9090/targets
- Пример графика Prometheus - http://localhost:9090/graph?g0.expr=jvm_memory_used_bytes&g0.tab=0&g0.stacked=0&g0.show_exemplars=0&g0.range_input=30m

## Grafana
- Dashboards - http://localhost:3000/dashboards
- Там есть из полезных JVM (Micrometer) и CPU usage

## Для деплоя
- Сейчас всё запушено вместе с локально собранными метриками
- Можно очистить всё, почистив содержимое /data/grafana и /prometheus/data
- Тут http://localhost:3000/datasources нужно добавить локальный prometheus, поискать его по имени и потом url заполнить на http://localhost:9090
- А потом нужно зайти http://localhost:3000/dashboards, дальше нажать import, ввести в первое поле 4701,
нажать load, снизу в Prometheus выбрать локлаьный Prometheus, сохранить
- Можно создать CPU usage - здесь http://localhost:3000/dashboards нажать на new dashboard, потом add new panel,
дальше внизу в Metrics browser ввести system_cpu_usage, потом нажать run query, он нарисует график, дальше можно время на last 15 минут поставить, например