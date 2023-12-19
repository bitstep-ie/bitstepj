package ie.bitstep.typesafety.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TypeSafeTest {
    static class ProfileId extends TypeSafe<String> {

        public ProfileId(String thing) {
            super(thing);
        }

        public static ProfileId of(String s) {
            return new ProfileId(s);
        }
    }

    static class AccountId extends TypeSafe<String> {

        public AccountId(String thing) {
            super(thing);
        }

        public static AccountId of(String thing) {
            return new AccountId(thing);
        }
    }

    void doCall(ProfileId profileId, AccountId accountId) {
        System.out.println(profileId.get());
        System.out.println(accountId.get());
    }

    @Test
    void test() {
        ProfileId profileId = ProfileId.of("1");
        ProfileId profileId2 = ProfileId.of("1");
        AccountId accountId = AccountId.of("1");

        doCall(profileId, accountId);

        assertThat(profileId)
                .isEqualTo(profileId2)
                .isNotEqualTo(accountId); // NOSONAR
    }
}