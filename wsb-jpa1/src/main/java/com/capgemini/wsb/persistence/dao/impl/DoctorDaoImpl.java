package com.capgemini.wsb.persistence.dao.impl;

import com.capgemini.wsb.persistence.dao.DoctorDao;
import com.capgemini.wsb.persistence.entity.DoctorEntity;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.enums.Specialization;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DoctorDaoImpl extends AbstractDao<DoctorEntity, Long> implements DoctorDao {
    @Override
    public List<DoctorEntity> findBySpecialization(Specialization specialization) {
         Query query = entityManager.createQuery("select d from DoctorEntity d " +
                 "where d.specialization = :spec").setParameter("spec", specialization);
        return query.getResultList();
    }

    @Override
    public long countNumOfVisitsWithPatient(String docFirstName, String docLastName, String patientFirstName, String patientLastName) {
        Query query = entityManager.createQuery("select d from DoctorEntity d " +
                "join d.visits v " +
                "where d.firstName = :docFirstName and d.lastName = :docLastName " +
                "and v.patient.firstName = :patientFirstName and v.patient.lastName = :patientLastName") //
                .setParameter("docFirstName", docFirstName) //
                .setParameter("docLastName", docLastName) //
                .setParameter("patientFirstName", patientFirstName) //
                .setParameter("patientLastName", patientLastName);
        return query.getResultList().size();
    }
}
