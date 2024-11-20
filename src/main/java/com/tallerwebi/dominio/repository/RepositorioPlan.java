package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.Plan;

public interface RepositorioPlan {
    Plan buscarPlanPorId(Long idPlan);
    Plan buscarPlanBronce();
}
