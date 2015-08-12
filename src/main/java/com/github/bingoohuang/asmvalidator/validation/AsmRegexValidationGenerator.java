package com.github.bingoohuang.asmvalidator.validation;

import com.github.bingoohuang.asmvalidator.AsmValidationGenerator;
import com.github.bingoohuang.asmvalidator.annotations.AsmConstraint;
import com.github.bingoohuang.asmvalidator.annotations.AsmRegex;
import com.github.bingoohuang.asmvalidator.asm.LocalIndices;
import com.github.bingoohuang.asmvalidator.ex.AsmValidatorBadArgException;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static com.github.bingoohuang.asmvalidator.utils.AsmValidators.addError;
import static com.github.bingoohuang.asmvalidator.utils.Asms.p;
import static com.github.bingoohuang.asmvalidator.utils.Asms.sig;
import static org.objectweb.asm.Opcodes.*;

public class AsmRegexValidationGenerator implements AsmValidationGenerator {
    @Override
    public void generateAsm(
            MethodVisitor mv, String fieldName, Class<?> fieldType,
            Annotation fieldAnnotation, LocalIndices localIndices,
            AsmConstraint constraint, String message
    ) {
        AsmRegex asmRegex = (AsmRegex) fieldAnnotation;
        String regex = asmRegex.value();

        try {
            Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            throw new AsmValidatorBadArgException(
                    "正则表达式错误:" + fieldAnnotation);
        }

        mv.visitVarInsn(ILOAD, localIndices.getStringLocalNullIndex());
        Label l1 = new Label();
        mv.visitJumpInsn(IFNE, l1);
        mv.visitVarInsn(ALOAD, localIndices.getStringLocalIndex());
        mv.visitLdcInsn(regex);
        mv.visitMethodInsn(INVOKEVIRTUAL, p(String.class), "matches",
                sig(boolean.class, String.class), false);
        mv.visitJumpInsn(IFNE, l1);
        addError(fieldName, mv, fieldAnnotation, constraint, message, localIndices, l1);
    }
}
