package com.blackshoe.esthete.service;

<<<<<<< HEAD
import io.jsonwebtoken.SignatureException;
=======
import io.jsonwebtoken.security.SignatureException;
>>>>>>> 529f1f3f26572dc140e5b44b95c32c6b23d6b217

import java.util.UUID;

public interface JwtService {
<<<<<<< HEAD
    public UUID extractUserId(String token) throws SignatureException;
=======

    UUID extractUserId(String token) throws SignatureException;
>>>>>>> 529f1f3f26572dc140e5b44b95c32c6b23d6b217
}
