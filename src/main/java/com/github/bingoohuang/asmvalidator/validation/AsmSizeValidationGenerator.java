package com.github.bingoohuang.asmvalidator.validation;

import com.github.bingoohuang.asmvalidator.AsmValidationGenerator;
import com.github.bingoohuang.asmvalidator.annotations.AsmConstraint;
import com.github.bingoohuang.asmvalidator.annotations.AsmSize;
import com.github.bingoohuang.asmvalidator.asm.LocalIndices;
import com.github.bingoohuang.asmvalidator.utils.Asms;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.lang.annotation.Annotation;

import static com.github.bingoohuang.asmvalidator.utils.AsmValidators.addError;
import static com.github.bingoohuang.asmvalidator.utils.Asms.p;
import static org.objectweb.asm.Opcodes.*;

public class AsmSizeValidationGenerator implements AsmValidationGenerator {
    @Override
    public void generateAsm(
            MethodVisitor mv, String fieldName, Class<?> fieldType,
            Annotation fieldAnnotation, LocalIndices localIndices,
            AsmConstraint constraint, String message
    ) {
        AsmSize asmSize = (AsmSize) fieldAnnotation;

        mv.visitVarInsn(ILOAD, localIndices.getStringLocalNullIndex());
        Label l1 = new Label();
        mv.visitJumpInsn(IFNE, l1);
        mv.visitVarInsn(ALOAD, localIndices.getStringLocalIndex());
        mv.visitMethodInsn(INVOKEVIRTUAL, p(String.class),
                "length", "()I", false);

        int size = asmSize.value();
        Asms.visitInt(mv, size);
        Label l2 = new Label();
        mv.visitJumpInsn(IF_ICMPEQ, l2);
        mv.visitLabel(l1);
        addError(fieldName, mv, fieldAnnotation, constraint, message, localIndices);
        mv.visitLabel(l2);
    }
}
