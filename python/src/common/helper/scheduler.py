from apscheduler.schedulers.background import BackgroundScheduler


def add_schedules(schedule_configs):
    scheduler = BackgroundScheduler()

    for sc in schedule_configs:
        scheduler.add_job(**sc)

    scheduler.start()
