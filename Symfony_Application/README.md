Symfony Application for Benchmark
=================

### Version
``` Version 1.0.0 ```

### System Requirements
>* [Netbeans 8.2](https://netbeans.org/downloads/)
>* [PHP  7.0](http://php.net/downloads.php)
>* [Apache2](https://www.digitalocean.com/community/tutorials/how-to-install-linux-apache-mysql-php-lamp-stack-on-ubuntu-14-04)
>* [MySQL](https://www.digitalocean.com/community/tutorials/how-to-install-mysql-on-ubuntu-14-04)
>* [Symfony 3.4](https://laravel.com/docs/5.1)
>* [Postman](https://www.getpostman.com/docs/postman/launching_postman/installation_and_updates)

### Dependencies
>* Composer https://getcomposer.org/download/

### Deployment instructions
- Composer Install
```
$ composer install
```

### Generate Schema
```
php bin/console doctrine:schema:update --force
```

### Run the application
```
$ php bin/console server:start
```

## License
Copyright © 2018 Nineleaps

ALL Rights Reserved, No part of this software can be modified or edited without permissions
