package by.itacademy.hibernate.dao;

import by.itacademy.hibernate.entity.Apartment;
import by.itacademy.hibernate.util.HibernateUtil;
import by.itacademy.hibernate.utils.TestDataImporter;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class ApartmentDaoTest {


    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private final ApartmentDao apartmentDao = ApartmentDao.getInstance();

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }

    @Test
    void findAllApplications() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Apartment> findAllApartment = apartmentDao.findAll(session);

        assertThat(findAllApartment.get(0).getAddress()).isEqualTo("generala belova 28");

        session.getTransaction().commit();
    }

    @Test
    void testGetAllUserBySize() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<String> allUsersByAddress = apartmentDao.getAllUsersByAddress(session, "generala belova 28");
        assertThat(allUsersByAddress.equals(Arrays.asList("BillGates", "SteveJobs", "SergeyBrin")));
    }

    @Test
    void testApartmentSize() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Integer apartmentSize = apartmentDao.getApartmentSize(session, "panfilova str 17");
        assertThat(apartmentSize.equals(59));
    }

    @Test
    void testFindApartmentsByRange() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<String> apartmentsByRange = apartmentDao.findApartmentsByRange(session, 20, 60);
        assertThat(apartmentsByRange.equals(Arrays.asList("generala belova 28", "panfilova str 17")));
    }

    @Test
    void testApartmentGtThenSize() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Apartment> apartmentGtThenSize = apartmentDao.getApartmentGtThenSize(session, 30);
        assertThat(apartmentGtThenSize.size()).isEqualTo(1);
    }


}
