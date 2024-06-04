package com.capgemini.wsb.persistence.dao.impl;

import com.capgemini.wsb.persistence.dao.PatientDao;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.enums.TreatmentType;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {


    @Override
    public List<PatientEntity> findByDoctor(String firstName, String lastName) {
        Query query = entityManager.createQuery("select p from PatientEntity p " +
                        "join p.visits v " +
                        "where v.doctor.firstName = :firstName and v.doctor.lastName = :lastName") //
                .setParameter("firstName", firstName) //
                .setParameter("lastName", lastName);
        return query.getResultList();

    }

    @Override
    public List<PatientEntity> findPatientsHavingTreatmentType(TreatmentType treatmentType) {
        Query query = entityManager.createQuery("select distinct p from PatientEntity p " +
                "join p.visits v " +
                "join v.medicalTreatments mt " +
                "where mt.type = :treatmentType")//
                .setParameter("treatmentType", treatmentType);
        return query.getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsSharingSameLocationWithDoc(String firstName, String lastName) {
        Query query = entityManager.createQuery("select  p from PatientEntity p " +
                        "join p.addresses a " +
                        "join a.doctors d " +
                        "where d.firstName = :docFirstName and d.lastName = :docLastName")//
                .setParameter("docFirstName", firstName)
                .setParameter("docLastName", lastName);
        return query.getResultList();

    }

    @Override
    public List<PatientEntity> findPatientsWithoutLocation() { // TODO - napisac query
        Query query = entityManager.createQuery("select  p from PatientEntity p where size(p.addresses) = 0");
        return query.getResultList();
    }
}
