/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.enumeration;

/**
 *
 * @author wjahoward
 */
public enum StatusName {
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    StatusName(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

