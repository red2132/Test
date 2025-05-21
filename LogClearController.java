package com.kt.partnership_batch.controller;

import com.kt.partnership_batch.common.code.BATCH_INFO;
import com.kt.partnership_batch.common.code.PERIOD;
import com.kt.partnership_batch.common.exception.BatchErrorException;
import com.kt.partnership_batch.dto.ClearStorageLife;
import com.kt.partnership_batch.service.LogClearService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/batch/v1/clear")
public class LogClearController {
    private final LogClearService logClearService;

    @Transactional
    @RequestMapping("/event-log")
    public void clearLogEventInfo(@RequestBody ClearStorageLife clearStorageLife) {
        final String batchId = BATCH_INFO.EVENT_INFO.getBatchId();
        final String batchName = BATCH_INFO.EVENT_INFO.getBatchName();
        
        log.info("{} 시작", batchName);
        LocalDateTime jobStartDatetime = LocalDateTime.now();

        try {
            if (clearStorageLife == null) {
                throw new BatchErrorException(batchName + " 보관주기 정보가 없어 중지되었습니다.",
                                          batchId,
                                          batchName
                );
                
            } else {
                Integer unit = clearStorageLife.getUnit();
                PERIOD period = clearStorageLife.getPeriod();

                String life = unit + " " + period.getFormatPostgreSql();
                log.info("[{}] 보관주기 [{}]로 동작합니다.", batchName, life);
                logClearService.clearLogEventInfo(life);
            }
        } catch (Exception e) {
            throw new BatchErrorException(batchName + " 처리 중 오류가 발생했습니다.\n이유: " + e.getMessage() + "\n담당자에게 문의바랍니다.",
                                      batchId,
                                      batchName,
                                      e);
        } finally {
            LocalDateTime jobFinishDatetime = LocalDateTime.now();
            Duration duration = Duration.between(jobStartDatetime, jobFinishDatetime);
            log.info("{} 종료 [경과시간 {}초]", batchName, duration.getSeconds());
        }
    }

    @Transactional
    @RequestMapping("/category-log")
    public void clearLogCategoryInfo(@RequestBody ClearStorageLife clearStorageLife) {
        final String batchId = BATCH_INFO.CATEGORY_INFO.getBatchId();
        final String batchName = BATCH_INFO.CATEGORY_INFO.getBatchName();
        
        log.info("{} 시작", batchName);
        LocalDateTime jobStartDatetime = LocalDateTime.now();

        try {
            if (clearStorageLife == null) {
                throw new BatchErrorException(batchName + " 보관주기 정보가 없어 중지되었습니다.",
                                          batchId,
                                          batchName
                );
                
            } else {
                Integer unit = clearStorageLife.getUnit();
                PERIOD period = clearStorageLife.getPeriod();

                String life = unit + " " + period.getFormatPostgreSql();
                log.info("[{}] 보관주기 [{}]로 작업합니다.", batchName, life);
                logClearService.clearLogCategoryInfo(life);
            }
        } catch (Exception e) {
            throw new BatchErrorException(batchName + " 처리 중 오류가 발생했습니다.\n이유: " + e.getMessage() + "\n담당자에게 문의바랍니다.",
                                      batchId,
                                      batchName,
                                      e
            );
            
        } finally {
            LocalDateTime jobFinishDatetime = LocalDateTime.now();
            Duration duration = Duration.between(jobStartDatetime, jobFinishDatetime);
            log.info("{} 종료 [경과시간 {}초]", batchName, duration.getSeconds());
        }
    }

    @Transactional
    @RequestMapping("/product-log")
    public void clearLogProductInfo(@RequestBody ClearStorageLife clearStorageLife) {
        final String batchId = BATCH_INFO.T_PRODUCT_INFO.getBatchId();
        final String batchName = BATCH_INFO.T_PRODUCT_INFO.getBatchName();

        log.info("{} 시작", batchName);
        LocalDateTime jobStartDatetime = LocalDateTime.now();

        try {
            if (clearStorageLife == null) {
                throw new BatchErrorException(batchName + " 보관주기 정보가 없어 중지되었습니다.",
                                          batchId,
                                          batchName
                );
            } else {
                Integer unit = clearStorageLife.getUnit();
                PERIOD period = clearStorageLife.getPeriod();

                String life = unit + " " + period.getFormatPostgreSql();
                log.info("[{}] 보관주기 [{}]로 작업합니다.", batchName, life);
                logClearService.clearLogProductInfo(life);
            }
        } catch (Exception e) {
            throw new BatchErrorException(batchName + " 처리 중 오류가 발생했습니다.\n이유: " + e.getMessage() + "\n담당자에게 문의바랍니다.",
                    batchId,
                    batchName,
                    e);
        } finally {
            LocalDateTime jobFinishDatetime = LocalDateTime.now();
            Duration duration = Duration.between(jobStartDatetime, jobFinishDatetime);
            log.info("{} 종료 [경과시간 {}초]", batchName, duration.getSeconds());
        }
    }

    @Transactional
    @RequestMapping("/card-log")
    public void clearLogCardInfo(@RequestBody ClearStorageLife clearStorageLife) {
        final String batchId = BATCH_INFO.T_P_CARD_INFO.getBatchId();
        final String batchName = BATCH_INFO.T_P_CARD_INFO.getBatchName();

        log.info("{} 시작", batchName);
        LocalDateTime jobStartDatetime = LocalDateTime.now();

        try {
            if (clearStorageLife == null) {
                throw new BatchErrorException(batchName + " 보관주기 정보가 없어 중지되었습니다.",
                        batchId,
                        batchName
                );
            } else {
                Integer unit = clearStorageLife.getUnit();
                PERIOD period = clearStorageLife.getPeriod();

                String life = unit + " " + period.getFormatPostgreSql();
                log.info("[{}] 보관주기 [{}]로 작업합니다.", batchName, life);
                logClearService.clearLogCardInfo(life);
            }
        } catch (Exception e) {
            throw new BatchErrorException(batchName + " 처리 중 오류가 발생했습니다.\n이유: " + e.getMessage() + "\n담당자에게 문의바랍니다.",
                    batchId,
                    batchName,
                    e);
        } finally {
            LocalDateTime jobFinishDatetime = LocalDateTime.now();
            Duration duration = Duration.between(jobStartDatetime, jobFinishDatetime);
            log.info("{} 종료 [경과시간 {}초]", batchName, duration.getSeconds());
        }
    }

    @Transactional
    @RequestMapping("/funeral-log")
    public void clearLogFuneralInfo(@RequestBody ClearStorageLife clearStorageLife) {
        final String batchId = BATCH_INFO.T_P_FUNERAL_INFO.getBatchId();
        final String batchName = BATCH_INFO.T_P_FUNERAL_INFO.getBatchName();

        log.info("{} 시작", batchName);
        LocalDateTime jobStartDatetime = LocalDateTime.now();

        try {
            if (clearStorageLife == null) {
                throw new BatchErrorException(batchName + " 보관주기 정보가 없어 중지되었습니다.",
                        batchId,
                        batchName
                );
            } else {
                Integer unit = clearStorageLife.getUnit();
                PERIOD period = clearStorageLife.getPeriod();

                String life = unit + " " + period.getFormatPostgreSql();
                log.info("[{}] 보관주기 [{}]로 작업합니다.", batchName, life);
                logClearService.clearLogFuneralInfo(life);
            }
        } catch (Exception e) {
            throw new BatchErrorException(batchName + " 처리 중 오류가 발생했습니다.\n이유: " + e.getMessage() + "\n담당자에게 문의바랍니다.",
                    batchId,
                    batchName,
                    e);
        } finally {
            LocalDateTime jobFinishDatetime = LocalDateTime.now();
            Duration duration = Duration.between(jobStartDatetime, jobFinishDatetime);
            log.info("{} 종료 [경과시간 {}초]", batchName, duration.getSeconds());
        }
    }

    @Transactional
    @RequestMapping("/repair-log")
    public void clearRepairLog(@RequestBody ClearStorageLife clearStorageLife) {
        final String batchId = BATCH_INFO.T_REPAIR_LOG.getBatchId();
        final String batchName = BATCH_INFO.T_REPAIR_LOG.getBatchName();
        log.info("{} 시작", batchName);
        LocalDateTime jobStartDatetime = LocalDateTime.now();

        try {
            if (clearStorageLife == null) {
                throw new BatchErrorException(batchName + " 보관주기 정보가 없어 중지되었습니다.",
                        batchId,
                        batchName
                );
            } else {
                Integer unit = clearStorageLife.getUnit();
                PERIOD period = clearStorageLife.getPeriod();

                String life = unit + " " + period.getFormatPostgreSql();
                log.info("[{}] 보관주기 [{}]로 작업합니다.", batchName, life);
                logClearService.clearRepairLog(life);
            }
        } catch (Exception e) {
            throw new BatchErrorException(batchName + " 처리 중 오류가 발생했습니다.\n이유: " + e.getMessage() + "\n담당자에게 문의바랍니다.",
                    batchId,
                    batchName,
                    e);
        } finally {
            LocalDateTime jobFinishDatetime = LocalDateTime.now();
            Duration duration = Duration.between(jobStartDatetime, jobFinishDatetime);
            log.info("{} 종료 [경과시간 {}초]", batchName, duration.getSeconds());
        }
    }

    @Transactional
    @RequestMapping("/codegroup-snapshot")
    public void clearCodeGroupSnapShot(@RequestBody ClearStorageLife clearStorageLife) {
        final String batchId = BATCH_INFO.T_CODE_GROUP_SNAPSHOT.getBatchId();
        final String batchName = BATCH_INFO.T_CODE_GROUP_SNAPSHOT.getBatchName();
        log.info("{} 시작", batchName);
        LocalDateTime jobStartDatetime = LocalDateTime.now();

        try {
            if (clearStorageLife == null) {
                throw new BatchErrorException(batchName + " 보관주기 정보가 없어 중지되었습니다.",
                        batchId,
                        batchName
                );
            } else {
                Integer unit = clearStorageLife.getUnit();
                PERIOD period = clearStorageLife.getPeriod();

                String life = unit + " " + period.getFormatPostgreSql();
                log.info("[{}] 보관주기 [{}]로 작업합니다.", batchName, life);
                logClearService.clearCodeGroupSnapShot(life);
            }
        } catch (Exception e) {
            throw new BatchErrorException(batchName + " 처리 중 오류가 발생했습니다.\n이유: " + e.getMessage() + "\n담당자에게 문의바랍니다.",
                    batchId,
                    batchName,
                    e);
        } finally {
            LocalDateTime jobFinishDatetime = LocalDateTime.now();
            Duration duration = Duration.between(jobStartDatetime, jobFinishDatetime);
            log.info("{} 종료 [경과시간 {}초]", batchName, duration.getSeconds());
        }
    }

    @Transactional
    @RequestMapping("/local-nav-log")
    public void clearLogLocalNav(@RequestBody ClearStorageLife clearStorageLife) {
        final String batchId = BATCH_INFO.T_LOCAL_NAV_HISTORY.getBatchId();
        final String batchName = BATCH_INFO.T_LOCAL_NAV_HISTORY.getBatchName();

        log.info("{} 시작", batchName);
        LocalDateTime jobStartDatetime = LocalDateTime.now();

        try {
            if (clearStorageLife == null) {
                throw new BatchErrorException(batchName + " 보관주기 정보가 없어 중지되었습니다.",
                        batchId,
                        batchName
                );
            } else {
                Integer unit = clearStorageLife.getUnit();
                PERIOD period = clearStorageLife.getPeriod();

                String life = unit + " " + period.getFormatPostgreSql();
                log.info("[{}] 보관주기 [{}]로 작업합니다.", batchName, life);
                logClearService.clearLogLocalNav(life);
            }
        } catch (Exception e) {
            throw new BatchErrorException(batchName + " 처리 중 오류가 발생했습니다.\n이유: " + e.getMessage() + "\n담당자에게 문의바랍니다.",
                    batchId,
                    batchName,
                    e);
        } finally {
            LocalDateTime jobFinishDatetime = LocalDateTime.now();
            Duration duration = Duration.between(jobStartDatetime, jobFinishDatetime);
            log.info("{} 종료 [경과시간 {}초]", batchName, duration.getSeconds());
        }
    }

    @Transactional
    @RequestMapping("/login-log")
    public void clearLoginLog(@RequestBody ClearStorageLife clearStorageLife) {
        final String batchId = BATCH_INFO.T_LOGIN_LOG.getBatchId();
        final String batchName = BATCH_INFO.T_LOGIN_LOG.getBatchName();

        log.info("{} 시작", batchName);
        LocalDateTime jobStartDatetime = LocalDateTime.now();

        try {
            if (clearStorageLife == null) {
                throw new BatchErrorException(batchName + " 보관주기 정보가 없어 중지되었습니다.",
                        batchId,
                        batchName
                );
            } else {
                Integer unit = clearStorageLife.getUnit();
                PERIOD period = clearStorageLife.getPeriod();

                String life = unit + " " + period.getFormatPostgreSql();
                log.info("[{}] 보관주기 [{}]로 작업합니다.", batchName, life);
                logClearService.clearLogLogin(life);
            }
        } catch (Exception e) {
            throw new BatchErrorException(batchName + " 처리 중 오류가 발생했습니다.\n이유: " + e.getMessage() + "\n담당자에게 문의바랍니다.",
                    batchId,
                    batchName,
                    e);
        } finally {
            LocalDateTime jobFinishDatetime = LocalDateTime.now();
            Duration duration = Duration.between(jobStartDatetime, jobFinishDatetime);
            log.info("{} 종료 [경과시간 {}초]", batchName, duration.getSeconds());
        }
    }

    @Transactional
    @RequestMapping("/report-member-log")
    public void clearLogReportMember(@RequestBody ClearStorageLife clearStorageLife) {
        final String batchId = BATCH_INFO.T_REPORT_MEMBER.getBatchId();
        final String batchName = BATCH_INFO.T_REPORT_MEMBER.getBatchName();

        log.info("{} 시작", batchName);
        LocalDateTime jobStartDatetime = LocalDateTime.now();

        try {
            if (clearStorageLife == null) {
                throw new BatchErrorException(batchName + " 보관주기 정보가 없어 중지되었습니다.",
                        batchId,
                        batchName
                );
            } else {
                Integer unit = clearStorageLife.getUnit();
                PERIOD period = clearStorageLife.getPeriod();

                String life = unit + " " + period.getFormatPostgreSql();
                log.info("[{}] 보관주기 [{}]로 작업합니다.", batchName, life);
                logClearService.clearLogReportMember(life);
            }
        } catch (Exception e) {
            throw new BatchErrorException(batchName + " 처리 중 오류가 발생했습니다.\n이유: " + e.getMessage() + "\n담당자에게 문의바랍니다.",
                    batchId,
                    batchName,
                    e);
        } finally {
            LocalDateTime jobFinishDatetime = LocalDateTime.now();
            Duration duration = Duration.between(jobStartDatetime, jobFinishDatetime);
            log.info("{} 종료 [경과시간 {}초]", batchName, duration.getSeconds());
        }
    }

    @Transactional
    @RequestMapping("/menu-log")
    public void clearMenuLog(@RequestBody ClearStorageLife clearStorageLife) {
        final String batchId = BATCH_INFO.T_MNG_MENU_LOG.getBatchId();
        final String batchName = BATCH_INFO.T_MNG_MENU_LOG.getBatchName();

        log.info("{} 시작", batchName);
        LocalDateTime jobStartDatetime = LocalDateTime.now();

        try {
            if (clearStorageLife == null) {
                throw new BatchErrorException(batchName + " 보관주기 정보가 없어 중지되었습니다.",
                        batchId,
                        batchName
                );
            } else {
                Integer unit = clearStorageLife.getUnit();
                PERIOD period = clearStorageLife.getPeriod();

                String life = unit + " " + period.getFormatPostgreSql();
                log.info("[{}] 보관주기 [{}]로 작업합니다.", batchName, life);
                logClearService.clearLogMenu(life);
            }
        } catch (Exception e) {
            throw new BatchErrorException(batchName + " 처리 중 오류가 발생했습니다.\n이유: " + e.getMessage() + "\n담당자에게 문의바랍니다.",
                    batchId,
                    batchName,
                    e);
        } finally {
            LocalDateTime jobFinishDatetime = LocalDateTime.now();
            Duration duration = Duration.between(jobStartDatetime, jobFinishDatetime);
            log.info("{} 종료 [경과시간 {}초]", batchName, duration.getSeconds());
        }
    }

    @Transactional
    @RequestMapping("/notice-log")
    public void clearLogNotice(@RequestBody ClearStorageLife clearStorageLife) {
        final String batchId = BATCH_INFO.T_ALERT_PLATFORM_LOG.getBatchId();
        final String batchName = BATCH_INFO.T_ALERT_PLATFORM_LOG.getBatchName();
        log.info("{} 시작", batchName);
        LocalDateTime jobStartDatetime = LocalDateTime.now();

        try {
            if (clearStorageLife == null) {
                throw new BatchErrorException(batchName + " 보관주기 정보가 없어 중지되었습니다.",
                        batchId,
                        batchName
                );
            } else {
                Integer unit = clearStorageLife.getUnit();
                PERIOD period = clearStorageLife.getPeriod();

                String life = unit + " " + period.getFormatPostgreSql();
                log.info("[{}] 보관주기 [{}]로 작업합니다.", batchName, life);
                logClearService.clearLogNotice(life);
            }
        } catch (Exception e) {
            throw new BatchErrorException(batchName + " 처리 중 오류가 발생했습니다.\n이유: " + e.getMessage() + "\n담당자에게 문의바랍니다.",
                    batchId,
                    batchName,
                    e);
        } finally {
            LocalDateTime jobFinishDatetime = LocalDateTime.now();
            Duration duration = Duration.between(jobStartDatetime, jobFinishDatetime);
            log.info("{} 종료 [경과시간 {}초]", batchName, duration.getSeconds());
        }
    }

    @Transactional
    @RequestMapping("/click-event-log")
    public void clearLogClickEvent(@RequestBody ClearStorageLife clearStorageLife) {
        final String batchId = BATCH_INFO.T_EXT_CLK_EVT_STAT.getBatchId();
        final String batchName = BATCH_INFO.T_EXT_CLK_EVT_STAT.getBatchName();

        log.info("{} 시작", batchName);
        LocalDateTime jobStartDatetime = LocalDateTime.now();

        try {
            if (clearStorageLife == null) {
                throw new BatchErrorException(batchName + " 보관주기 정보가 없어 중지되었습니다.",
                        batchId,
                        batchName
                );
            } else {
                Integer unit = clearStorageLife.getUnit();
                PERIOD period = clearStorageLife.getPeriod();

                String life = unit + " " + period.getFormatPostgreSql();
                log.info("[{}] 보관주기 [{}]로 작업합니다.", batchName, life);
                logClearService.clearLogNotice(life);
            }
        } catch (Exception e) {
            throw new BatchErrorException(batchName + " 처리 중 오류가 발생했습니다.\n이유: " + e.getMessage() + "\n담당자에게 문의바랍니다.",
                    batchId,
                    batchName,
                    e);
        } finally {
            LocalDateTime jobFinishDatetime = LocalDateTime.now();
            Duration duration = Duration.between(jobStartDatetime, jobFinishDatetime);
            log.info("{} 종료 [경과시간 {}초]", batchName, duration.getSeconds());
        }
    }

    @Transactional
    @RequestMapping("/home-appl-log")
    public void clearLogHomeApplInfo(@RequestBody ClearStorageLife clearStorageLife) {
        final String batchId = BATCH_INFO.T_P_HOME_APPL_INFO.getBatchId();
        final String batchName = BATCH_INFO.T_P_HOME_APPL_INFO.getBatchName();

        log.info("{} 시작", batchName);
        LocalDateTime jobStartDatetime = LocalDateTime.now();

        try {
            if (clearStorageLife == null) {
                throw new BatchErrorException(batchName + " 보관주기 정보가 없어 중지되었습니다.",
                        batchId,
                        batchName
                );
            } else {
                Integer unit = clearStorageLife.getUnit();
                PERIOD period = clearStorageLife.getPeriod();

                String life = unit + " " + period.getFormatPostgreSql();
                log.info("[{}] 보관주기 [{}]로 작업합니다.", batchName, life);
                logClearService.clearLogHomeApplInfo(life);
            }
        } catch (Exception e) {
            throw new BatchErrorException(batchName + " 처리 중 오류가 발생했습니다.\n이유: " + e.getMessage() + "\n담당자에게 문의바랍니다.",
                    batchId,
                    batchName,
                    e);
        } finally {
            LocalDateTime jobFinishDatetime = LocalDateTime.now();
            Duration duration = Duration.between(jobStartDatetime, jobFinishDatetime);
            log.info("{} 종료 [경과시간 {}초]", batchName, duration.getSeconds());
        }
    }
}
