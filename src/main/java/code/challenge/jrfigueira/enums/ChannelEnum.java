package code.challenge.jrfigueira.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ChannelEnum {
    CLIENT(1), ATM(0), INTERNAL(-1);

    private int id;

    public int getChannelId(){
        return id;
    }
}
