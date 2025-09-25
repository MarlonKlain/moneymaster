package com.moneymaster.moneymaster.service.dashboard;

import com.moneymaster.moneymaster.model.dto.DashboardDto;

import java.util.UUID;

public interface DashboardService {
    DashboardDto getDashboardSummary(UUID userId);
}
