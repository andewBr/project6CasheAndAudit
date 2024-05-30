package by.itacademy.hibernate.dao;

import by.itacademy.hibernate.entity.Apartment;
import by.itacademy.hibernate.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

import static by.itacademy.hibernate.entity.QApartment.apartment;
import static by.itacademy.hibernate.entity.QUser.user;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApartmentDao {

    public static final ApartmentDao INSTANCE = new ApartmentDao();

    /**
     * Возвращает всех aparemnt
     */
    public List<Apartment> findAll(Session session) {
        return new JPAQuery<Apartment>(session)
                .select(apartment)
                .from(apartment)
                .fetch();
    }

    /**
     * Возвращает по адресу кол-во метров в кваррире
     */
    public Integer getApartmentSize(Session session, String address) {
        return new JPAQuery<Apartment>(session)
                .select(apartment.size)
                .from(apartment)
                .where(apartment.address.eq(address))
                .fetchOne();
    }

    /**
     * Возвращает всех user.username по указанному адресу
     */
    public List<String> getAllUsersByAddress(Session session, String address) {
        return new JPAQuery<String>(session)
                .select(user.username)
                .from(user)
                .join(user.apartments(), apartment)
                .where(apartment.address.eq(address)).fetch();
    }

    /**
     * Возвращает все квартиры > указанного размера
     */
    public List<Apartment> getApartmentGtThenSize(Session session, Integer size) {
        return new JPAQuery<Apartment>(session)
                .select(apartment)
                .from(apartment)
                .where(apartment.size.gt(size)).fetch();
    }

    /**
     * Возвращает апартаменты которые находятся в диапазоне между minSize maxSize
     */
    public List<String> findApartmentsByRange(Session session, int minSize, int maxSize) {
        return new JPAQuery<Apartment>(session)
                .select(apartment.address)
                .from(apartment)
                .where(apartment.size.between(minSize, maxSize))
                .fetch();
    }

    public static ApartmentDao getInstance() {
        return INSTANCE;
    }

}
