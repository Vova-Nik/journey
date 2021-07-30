package org.hillel.service;

import org.hillel.dto.dto.QueryParam;
import org.hillel.exceptions.UserAPIException;
import org.hillel.persistence.entity.UserEntity;
import org.hillel.persistence.jpa.repository.UserJPARepository;
import org.hillel.persistence.jpa.repository.specification.ClientSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service("ClientService")
public class UserService extends EntityServiceImplementation<UserEntity, Long> {

    private final UserJPARepository userRepository;

    @Autowired
    public UserService(UserJPARepository userRepository) {
        super(UserEntity.class, userRepository);
        this.userRepository = userRepository;
    }

    @Override
    boolean isValid(UserEntity user) {
        return user.isValid();
    }

    @Transactional
    public UserEntity create(UserEntity user){
        if(!user.isValid()){
            return new UserEntity();
        }
        if(existsByEmail(user.getEmail())){
            return new UserEntity();
        }
        try {
            return userRepository.save(user);
        }catch (IllegalArgumentException e){
            return new UserEntity();
        }
    }

    @Transactional
    public UserEntity remove(UserEntity user){
        if(user.getId()==null || !user.isValid()){
            return new UserEntity();
        }
        UserEntity userEntityToDelete = userRepository.findById(user.getId()).orElseThrow(() ->new UserAPIException("UserService.remove can not find user" + user));
        if(!userEntityToDelete.equals(user)){
            return new UserEntity();
        }
        try {
            userRepository.delete(user);
        }catch (IllegalArgumentException e){
            return new UserEntity();
        }
        return user;
    }

    @Transactional(readOnly = true)
    public List<UserEntity> getFiltered(QueryParam param) {
        if (Objects.isNull(param))
            param = new QueryParam();
        checkInput(param);
        return userRepository.findAll(ClientSpecification.byQueryParam(param));
    }

    @Transactional(readOnly = true)
    public Page<UserEntity> getFilteredPaged(QueryParam param) {
        if (Objects.isNull(param))
            param = new QueryParam();
        param = checkInput(param);
        PageRequest  page = PageRequest.of(param.getPageNumber(), param.getPageSize());
        return userRepository.findAll(ClientSpecification.byQueryParam(param), page);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        if (StringUtils.isEmpty(email)){
            return false;
        }
        List<UserEntity> users = userRepository.findByEmail(email);
        return !users.isEmpty();
    }

    @Transactional(readOnly = true)
    public UserEntity getByEmail(String email) {
        if (StringUtils.isEmpty(email)){
            throw new UserAPIException("UserService.getByEmail - can not find  " + email);
        }
        List<UserEntity> users = userRepository.findByEmail(email);
        return users.get(0);
    }

    @Override
    public UserEntity findById(Long id){
        UserEntity userEntity = new UserEntity();
        try{
            userEntity = super.findById(id);
        }catch (IllegalArgumentException e){
            throw new UserAPIException("UserService.getById - can not find user having id = " + id);
        }
        return  userEntity;
    }

    @Transactional
    public UserEntity update(UserEntity user){
        if(user.getId()==null || !user.isValid()){
            return new UserEntity();
        }
        UserEntity userEntityOld = userRepository.findById(user.getId()).orElseThrow(() ->new UserAPIException("UserService.remove can not find user" + user));
        System.out.println(userEntityOld);
//        userEntityOld.setName(user.getName());
//        userEntityOld.setSurname(user.getSurname());
//        userEntityOld.setEmail(user.getEmail());
//        userEntityOld.setPwd(user.getPwd());
//        userEntityOld.setCreationDate(user.getCreationDate());
//        userEntityOld.setActive(user.isActive());
        userEntityOld.update(user);
        System.out.println(userEntityOld);
        try {
            userRepository.save(user);
        }catch (IllegalArgumentException e){
            return new UserEntity();
        }
        return user;
    }

    private QueryParam checkInput(QueryParam param) {
        if (Objects.isNull(param))
            param = new QueryParam();
        Field[] allFields = UserEntity.class.getDeclaredFields();
        String column = param.getSortColumn();

        boolean checked = Arrays.stream(allFields).anyMatch(field ->
                column.equals(field.getName()));
        if (column.equals("id"))
            checked = true;
        if (!checked)
            throw new UserAPIException("Insufficient column name = \""+ column + "\" for sorting of Client");
        return param;
    }
}
