package com.kt.partnership_batch.scheduler;

import com.kt.partnership_batch.common.code.BATCH_INFO;
import com.kt.partnership_batch.common.code.PERIOD;
import com.kt.partnership_batch.common.exception.BatchErrorException;
import com.kt.partnership_batch.controller.LogClearController;
import com.kt.partnership_batch.controller.MobinsController;
import com.kt.partnership_batch.controller.StatController;
import com.kt.partnership_batch.controller.TestController;
import com.kt.partnership_batch.dto.ClearStorageLife;
import com.kt.partnership_batch.dto.ReqCommonStat;
import com.kt.partnership_batch.service.ErrorLogService;
import com.kt.partnership_batch.service.InfoManagerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SaveDreamScheduler {
    // * 배치 추가시 BATCH_INFO enum 업데이트 요망 *
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private final LogClearController logClearController;
    private final StatController statController;
    private final MobinsController mobinsController;

    private final TestController testController;

    private final ErrorLogService errorLogService;

    private final InfoManagerService infoManagerService;

    @Value("${savedream.init-run.log-clear}")
    private String isLogClear;
    @Value("${savedream.init-run.stat-local}")
    private String isStatLocal;
    @Value("${savedream.init-run.mobins-insurance}")
    private String isMobinsInsurance;

    @PostConstruct
    public void initialize() {
        if ("Y".equals(isLogClear)) {
            periodicClearLog();
        }
        if ("Y".equals(isStatLocal)) {
            statData();
        }
        if ("Y".equals(isMobinsInsurance)) {
            mobinsInsuranceData();
        }
    }


    @Value("${savedream.log-storage.event_info}")
    private String storageEventInfo;
    @Value("${savedream.log-storage.category_info}")
    private String storageCategoryInfo;
    @Value("${savedream.log-storage.product_info}")
    private String storageProductInfo;
    @Value("${savedream.log-storage.card_info}")
    private String storageCardInfo;
    @Value("${savedream.log-storage.funeral_info}")
    private String storageFuneralInfo;
    @Value("${savedream.log-storage.home_appl_info}")
    private String storageHomeApplInfo;
    @Value("${savedream.log-storage.repair}")
    private String storageRepair;
    @Value("${savedream.log-storage.code_group}")
    private String storageCodeGroupSnapShot;
    @Value("${savedream.log-storage.local_nav}")
    private String storageLocalNav;
    @Value("${savedream.log-storage.login}")
    private String storageLogin;
    @Value("${savedream.log-storage.report_member}")
    private String storageReportMember;
    @Value("${savedream.log-storage.menu}")
    private String storageMenu;
    @Value("${savedream.log-storage.alert_platform}")
    private String storageAlertPlatform;
    @Value("${savedream.log-storage.click_event}")
    private String storageClickEvent;

    @Scheduled(cron = "00 00 01 * * ?")
    public void periodicClearLog() {
        log.info("이력 정리 작업을 진행합니다.");
        Map<String, Future<?>> task = new LinkedHashMap<>();
        task.put(BATCH_INFO.EVENT_INFO.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            String[] raw = storageEventInfo.split(" ");
            Integer unit = Integer.parseInt(raw[0]);
            PERIOD period = PERIOD.fromPostgre(raw[1]);

            logClearController.clearLogEventInfo(ClearStorageLife.of(unit, period));
        }));
        task.put(BATCH_INFO.CATEGORY_INFO.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            String[] raw = storageCategoryInfo.split(" ");
            Integer unit = Integer.parseInt(raw[0]);
            PERIOD period = PERIOD.fromPostgre(raw[1]);

            logClearController.clearLogCategoryInfo(ClearStorageLife.of(unit, period));
        }));
        task.put(BATCH_INFO.T_PRODUCT_INFO.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            String[] raw = storageProductInfo.split(" ");
            Integer unit = Integer.parseInt(raw[0]);
            PERIOD period = PERIOD.fromPostgre(raw[1]);

            logClearController.clearLogProductInfo(ClearStorageLife.of(unit, period));
        }));
        task.put(BATCH_INFO.T_P_CARD_INFO.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            String[] raw = storageCardInfo.split(" ");
            Integer unit = Integer.parseInt(raw[0]);
            PERIOD period = PERIOD.fromPostgre(raw[1]);

            logClearController.clearLogCardInfo(ClearStorageLife.of(unit, period));
        }));
        task.put(BATCH_INFO.T_P_HOME_APPL_INFO.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            String[] raw = storageHomeApplInfo.split(" ");
            Integer unit = Integer.parseInt(raw[0]);
            PERIOD period = PERIOD.fromPostgre(raw[1]);

            logClearController.clearLogHomeApplInfo(ClearStorageLife.of(unit, period));
        }));
        task.put(BATCH_INFO.T_P_FUNERAL_INFO.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            String[] raw = storageFuneralInfo.split(" ");
            Integer unit = Integer.parseInt(raw[0]);
            PERIOD period = PERIOD.fromPostgre(raw[1]);

            logClearController.clearLogFuneralInfo(ClearStorageLife.of(unit, period));
        }));
        task.put(BATCH_INFO.T_REPAIR_LOG.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            String[] raw = storageRepair.split(" ");
            Integer unit = Integer.parseInt(raw[0]);
            PERIOD period = PERIOD.fromPostgre(raw[1]);

            logClearController.clearRepairLog(ClearStorageLife.of(unit, period));
        }));
        task.put(BATCH_INFO.T_CODE_GROUP_SNAPSHOT.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            String[] raw = storageCodeGroupSnapShot.split(" ");
            Integer unit = Integer.parseInt(raw[0]);
            PERIOD period = PERIOD.fromPostgre(raw[1]);

            logClearController.clearCodeGroupSnapShot(ClearStorageLife.of(unit, period));
        }));
        task.put(BATCH_INFO.T_LOCAL_NAV_HISTORY.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            String[] raw = storageLocalNav.split(" ");
            Integer unit = Integer.parseInt(raw[0]);
            PERIOD period = PERIOD.fromPostgre(raw[1]);

            logClearController.clearLogLocalNav(ClearStorageLife.of(unit, period));
        }));
        task.put(BATCH_INFO.T_LOGIN_LOG.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            String[] raw = storageLogin.split(" ");
            Integer unit = Integer.parseInt(raw[0]);
            PERIOD period = PERIOD.fromPostgre(raw[1]);

            logClearController.clearLoginLog(ClearStorageLife.of(unit, period));
        }));
        task.put(BATCH_INFO.T_REPORT_MEMBER.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            String[] raw = storageReportMember.split(" ");
            Integer unit = Integer.parseInt(raw[0]);
            PERIOD period = PERIOD.fromPostgre(raw[1]);

            logClearController.clearLogReportMember(ClearStorageLife.of(unit, period));
        }));
        task.put(BATCH_INFO.T_MNG_MENU_LOG.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            String[] raw = storageMenu.split(" ");
            Integer unit = Integer.parseInt(raw[0]);
            PERIOD period = PERIOD.fromPostgre(raw[1]);

            logClearController.clearMenuLog(ClearStorageLife.of(unit, period));
        }));
        task.put(BATCH_INFO.T_ALERT_PLATFORM_LOG.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            String[] raw = storageAlertPlatform.split(" ");
            Integer unit = Integer.parseInt(raw[0]);
            PERIOD period = PERIOD.fromPostgre(raw[1]);

            logClearController.clearLogNotice(ClearStorageLife.of(unit, period));
        }));
        task.put(BATCH_INFO.T_EXT_CLK_EVT_LOG.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            String[] raw = storageClickEvent.split(" ");
            Integer unit = Integer.parseInt(raw[0]);
            PERIOD period = PERIOD.fromPostgre(raw[1]);

            logClearController.clearLogClickEvent(ClearStorageLife.of(unit, period));
        }));

        /* JOB CLEAR */
        memoryClear(task);
    }

    @Scheduled(cron = "00 00 00 * * ?")
    public void statData() {
        log.info("통계 작업을 진행합니다.");
        final LocalDate BAS_DT = LocalDate.now().minusDays(1); /* 기준일자 전일 */
        Map<String, Future<?>> task = new LinkedHashMap<>();
        task.put(BATCH_INFO.T_LOCAL_NAV_STAT.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            statController.runStatLocalNav(ReqCommonStat.of(BAS_DT.format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
        }));
        task.put(BATCH_INFO.T_CATEGORY_VIEW_STAT.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            statController.runStatCategoryView(ReqCommonStat.of(BAS_DT.format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
        }));
        task.put(BATCH_INFO.T_PRODUCT_VIEW_STAT.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            statController.runStatProductView(ReqCommonStat.of(BAS_DT.format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
        }));
        task.put(BATCH_INFO.T_EXT_CLK_EVT_STAT.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            statController.runStatClickEvent(ReqCommonStat.of(BAS_DT.format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
        }));

        /* JOB CLEAR */
        memoryClear(task);
    }

    @Scheduled(cron = "00 00 04 * * ?")
    public void mobinsInsuranceData() {
        log.info("모빈스 데이터 수집작업을 진행합니다.");
        final LocalDate BAS_DT = LocalDate.now().minusDays(1); /* 기준일자 전일 */
        Map<String, Future<?>> task = new LinkedHashMap<>();
        task.put(BATCH_INFO.T_MOBINS_PET_INS.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            mobinsController.extractMobinsPet(ReqCommonStat.of(BAS_DT.format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
        }));
        task.put(BATCH_INFO.T_MOBINS_TRIP_INS.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            mobinsController.extractMobinsTrip(ReqCommonStat.of(BAS_DT.format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
        }));
        task.put(BATCH_INFO.T_MOBINS_OWNER_INS.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            mobinsController.extractMobinsOwner(ReqCommonStat.of(BAS_DT.format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
        }));
        task.put(BATCH_INFO.T_MOBINS_TRACKING_LOG.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            mobinsController.runStatMobinsTracking(ReqCommonStat.of(BAS_DT.format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
        }));

        /* JOB CLEAR */
        memoryClear(task);
    }

    @Scheduled(cron = "00 00 06 * * ?")
    public void mobinsStatData() {
        log.info("모빈스 데이터 통계작업을 진행합니다.");
        final LocalDate BAS_DT = LocalDate.now().minusDays(1); /* 기준일자 전일 */
        Map<String, Future<?>> task = new LinkedHashMap<>();
        task.put(BATCH_INFO.T_MOBINS_STAT.getBatchId(), threadPoolTaskExecutor.submit(() -> {
            mobinsController.runStatMobinsTracking(ReqCommonStat.of(BAS_DT.format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
        }));

        /* JOB CLEAR */
        memoryClear(task);
    }

    // TODO: 전시기간 초과시 전시여부 N
//    @Scheduled(cron = "00 * * * * ?")
//    public void finishDatetimeProduct() {
//        Map<String, Future<?>> task = new LinkedHashMap<>();
//        task.put(BATCH_INFO.FINISH_PRODUCT.getBatchId(), threadPoolTaskExecutor.submit(infoManagerService::finishProduct));
//        task.put(BATCH_INFO.FINISH_EVENT.getBatchId(), threadPoolTaskExecutor.submit(infoManagerService::finishEvent));
//
//        memoryClear(task);
//    }

    @Scheduled(cron = "00 * * * * ?")
    public void test() {
        Map<String, Future<?>> task = new LinkedHashMap<>();
        task.put("test", threadPoolTaskExecutor.submit(testController::errorTest));
        memoryClear(task);
    }

    private void memoryClear(Map<String, Future<?>> task) {
        task.entrySet().parallelStream().forEach(entry -> {
            String jobName = entry.getKey();
            Future<?> jobNode = entry.getValue();

            try {
                jobNode.get();
                log.info("{} work finished.", jobName);
            } catch (Exception e) {
                log.error("{} work error: {}", jobName, e.getMessage());
            }
        });
        task.clear();
    }
}
