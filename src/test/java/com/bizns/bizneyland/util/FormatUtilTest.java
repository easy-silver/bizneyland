package com.bizns.bizneyland.util;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class FormatUtilTest {

    @Test
    public void 전화번호_하이픈_추가() {
        Assertions.assertThat(FormatUtil.addHyphenPhoneNumber("01066491441")).isEqualTo("010-6649-1441");
        Assertions.assertThat(FormatUtil.addHyphenPhoneNumber("15881234")).isEqualTo("1588-1234");
        Assertions.assertThat(FormatUtil.addHyphenPhoneNumber("021234567")).isEqualTo("02-123-4567");
        Assertions.assertThat(FormatUtil.addHyphenPhoneNumber("0328154553")).isEqualTo("032-815-4553");
        Assertions.assertThat(FormatUtil.addHyphenPhoneNumber("03212341234")).isEqualTo("032-1234-1234");
    }

}