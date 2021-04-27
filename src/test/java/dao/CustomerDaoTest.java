package dao;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.bean.UserRole;
import by.victor.jwd.dao.CustomerDAO;
import by.victor.jwd.dao.DAOProvider;
import by.victor.jwd.dao.exception.DAOException;
import by.victor.jwd.service.ConnectionService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerDaoTest {

    private final static CustomerDAO customerDao = DAOProvider.getInstance().getCustomerDAO();

    @BeforeAll
    void initPool (){
        ConnectionService connectionService = ServiceProvider.getInstance().getConnectionService();
        try {
            connectionService.initConnectionPool();
        } catch (ServiceException e) {
            fail(e);
        }
    }

    @AfterAll
    void destroyPool (){
        ConnectionService connectionService = ServiceProvider.getInstance().getConnectionService();
        try {
            connectionService.destroyConnectionPool();
        } catch (ServiceException e) {
            fail(e);
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class GettingTests {

        private final Customer customerForAdd = new Customer("Bob","someemail@email.com","bob123");

        @BeforeAll
        void addCustomer(){
            try {
                customerDao.addNewCustomer(customerForAdd);
            } catch (DAOException e) {
                fail(e);
            }
        }

        @AfterAll
        void deleteCustomer () {
            try {
                customerDao.deleteCustomer(customerForAdd.getEmail());
            } catch (DAOException e) {
                fail(e);
            }
        }

        @Test
        void usersWithSameEmailShouldBeEquals() {
            Customer actual = null;
            try {
                actual = customerDao.getCustomerByEmail(customerForAdd.getEmail());
            } catch (DAOException e) {
                fail(e);
            }
            assertEquals(customerForAdd, actual);
        }

        @Test
        void usersWithSameEmailAndPasswordShouldBeEquals() {
            Customer actual = null;
            try {
                actual = customerDao.getCustomerByEmailAndPassword(customerForAdd.getEmail(), customerForAdd.getPassword());
            } catch (DAOException e) {
                fail(e);
            }
            assertEquals(customerForAdd, actual);
        }

        @Test
        void addedUserShouldExists() {
            boolean expected = false;
            try {
                expected = customerDao.isCustomerExists(customerForAdd.getEmail());
            } catch (DAOException e) {
                fail(e);
            }
            assertTrue(expected);
        }

    }


    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class EditingTests {

        private Customer customerForChange;
        private static final String EMAIL_FOR_CHECK = "email_for_check@check.com";
        private static final String NAME_FOR_CHECK = "Some Name";
        private static final String PASSWORD_FOR_CHECK = "some_password";

        @BeforeEach
        void createCustomerForChange(){
            customerForChange = new Customer(NAME_FOR_CHECK, EMAIL_FOR_CHECK, PASSWORD_FOR_CHECK);
            try {
                customerDao.addNewCustomer(customerForChange);
            } catch (DAOException e) {
                fail(e);
            }
        }

        @AfterEach
        void deleteCustomerForChange(){
            try {
                customerDao.deleteCustomer(customerForChange.getEmail());
            } catch (DAOException e) {
                fail(e);
            }
        }

        @Test
        void roleShouldBeChangedCorrectly() {
            try {
                Customer admin = customerDao.getCustomerByEmail(EMAIL_FOR_CHECK);
                admin.setRole(UserRole.ADMIN);
                Customer blocked = customerDao.getCustomerByEmail(EMAIL_FOR_CHECK);
                blocked.setRole(UserRole.BLOCK);
                Customer user = customerDao.getCustomerByEmail(EMAIL_FOR_CHECK);
                user.setRole(UserRole.USER);

                customerDao.changeRole(EMAIL_FOR_CHECK, UserRole.ADMIN);
                Customer adminActual = customerDao.getCustomerByEmail(EMAIL_FOR_CHECK);
                customerDao.changeRole(EMAIL_FOR_CHECK, UserRole.BLOCK);
                Customer blockedActual = customerDao.getCustomerByEmail(EMAIL_FOR_CHECK);
                customerDao.changeRole(EMAIL_FOR_CHECK, UserRole.USER);
                Customer userActual = customerDao.getCustomerByEmail(EMAIL_FOR_CHECK);

                assertAll(
                        () -> assertEquals(admin, adminActual),
                        () -> assertEquals(blocked, blockedActual),
                        () -> assertEquals(user, userActual)
                );
            } catch (DAOException e) {
                fail(e);
            }
        }

        @Test
        void updateShouldBeCorrect() {
            try {
                String nameExpected = "Bill";
                String phoneExpected = "+375441234567";
                String countryExpected = "Uganda";
                String addressExpected = "Patrice Lumumba street 11,95";

                customerForChange.setName(nameExpected);
                customerForChange.setPhone(phoneExpected);
                customerForChange.setCountry(countryExpected);
                customerForChange.setAddress(addressExpected);

                customerDao.updateCustomer(customerForChange);
                Customer actual = customerDao.getCustomerByEmail(EMAIL_FOR_CHECK);

                assertAll(
                        () -> assertEquals(nameExpected, actual.getName()),
                        () -> assertEquals(phoneExpected, actual.getPhone()),
                        () -> assertEquals(countryExpected,actual.getCountry()),
                        () -> assertEquals(addressExpected,actual.getAddress())
                );
            } catch (DAOException e) {
                fail(e);
            }
        }

        @Test
        void passwordShouldBeUpdatedCorrectly() {
            String expected = "some_new_password";
            String actual = "";
            try {
                customerForChange.setPassword(expected);
                customerDao.updatePassword(customerForChange.getEmail(), expected);
                Customer actualCustomer = customerDao.getCustomerByEmail(EMAIL_FOR_CHECK);
                actual = actualCustomer.getPassword();
            } catch (DAOException e) {
                fail(e);
            }
            assertEquals(expected, actual);
        }

    }
}
