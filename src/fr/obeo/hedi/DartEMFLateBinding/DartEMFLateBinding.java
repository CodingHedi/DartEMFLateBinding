package fr.obeo.hedi.DartEMFLateBinding;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class DartEMFLateBinding {

	public static void main(String[] args) {

		resolveEMFTest();
	}

	private static void resolveEMFTest() {

		int numElements = 0;

		var registry = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> map = registry.getExtensionToFactoryMap();
		map.put("*", new XMIResourceFactoryImpl());

		var resourceSet = new ResourceSetImpl();

		var metaURI = URI.createFileURI("model/dart.ecore");
		var modelURI = URI.createFileURI("model/dartlang.dartspec");

		Resource metaResource = resourceSet.getResource(metaURI, true);
		var ePackage = (EPackage) metaResource.getContents().get(0);
		EPackage.Registry.INSTANCE.put(ePackage.getNsURI(), ePackage);

		Resource modelResource = resourceSet.getResource(modelURI, true);

		Iterator<EObject> resourceIterator = modelResource.getAllContents();
		System.out.println("label de tous les éléments du modèle : ");

		while (resourceIterator.hasNext()) {
			numElements++;
			var eObject = (DynamicEObjectImpl) resourceIterator.next();

			if (eObject.eContents().isEmpty()) {
				System.out.println(eObject.dynamicGet(0));
			} else {
				System.out.println(eObject.eContainer());
			}
		}
		System.out.println("nombre d’éléments dans le modèle : " + numElements);

//		EList<EClassifier> eClassifiers = ePackage.getEClassifiers();
//
//		for (EClassifier eClassifier : eClassifiers) {
//			System.out.println(eClassifier.getName());
//		}

	}
}
