package by.itacademy.hibernate;

import by.itacademy.hibernate.dao.ApartmentDao;
import by.itacademy.hibernate.entity.Apartment;
import by.itacademy.hibernate.entity.User;
import by.itacademy.hibernate.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class HibernateRunner {
    private static final ApartmentDao apartmentDao = ApartmentDao.getInstance();


    public static void main(String[] args) {
        @Cleanup Session session = HibernateUtil.buildSessionFactory().openSession();
        session.beginTransaction();

        Apartment apartment = session.find(Apartment.class, 1l);
        apartment.setSize(apartment.getSize() + 10);

        session.getTransaction().commit();
    }
}