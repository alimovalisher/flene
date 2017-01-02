package com.fnklabs.flene.application.http.jtwig;

import com.google.common.base.Optional;
import org.jtwig.property.PropertyResolveRequest;
import org.jtwig.property.PropertyResolver;
import org.jtwig.reflection.model.Value;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Collectors;

class FieldAsMethodPropertyResolver implements PropertyResolver {
    @Override
    public Optional<Value> resolve(PropertyResolveRequest request) {
        Value entity = request.getEntity();

        Class<?>[] argumentsType = request.getArguments()
                                          .stream()
                                          .map(Object::getClass)
                                          .collect(Collectors.toList())
                                          .toArray(new Class[request.getArguments().size()]);

        try {
            Method method = entity.getValue().getClass().getDeclaredMethod(request.getPropertyName(), argumentsType);

            return Optional.of(new Value(method.invoke(request.getEntity().getValue(), request.getArguments().toArray())));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return Optional.absent();
    }
}
