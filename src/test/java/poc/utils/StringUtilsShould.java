package poc.utils;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsShould {

    @Test
    public void detect_null_string(){
        String str = null;
        assertThat(StringUtils.hasText(str)).isFalse();
    }

    @Test
    public void detect_empty_string(){
        String str = "";
        assertThat(StringUtils.hasText(str)).isFalse();
    }

    @Test
    public void detect_whitespaces(){
        String str = "a b";
        assertThat(StringUtils.hasText(str)).isFalse();
    }

    @Test
    public void accept_valid_text_no_whitespaces(){
        String str = "validText";
        assertThat(StringUtils.hasText(str)).isTrue();
    }
}