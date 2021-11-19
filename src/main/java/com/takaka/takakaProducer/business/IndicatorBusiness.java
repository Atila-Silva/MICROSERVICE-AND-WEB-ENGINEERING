package com.takaka.takakaProducer.business;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.takaka.takakaProducer.exception.ResposeBusinessException;
import com.takaka.takakaProducer.model.IndicatorsModel;

@Component
public class IndicatorBusiness {
	@Value("${rest.exception.business.contains-teste}")
	private String containsTeste;

	public IndicatorsModel applyBusiness(IndicatorsModel indicator) throws ResposeBusinessException {

		String descriptionku = changeDescriptionToUpperCase(indicator.getSeries_description());
		indicator.setSeries_description(descriptionku);

		verifyNomeGeoArea(indicator.getGeo_area_name());

		return indicator;
	}

	protected String changeDescriptionToUpperCase(String sku) {
		return sku.toUpperCase();
	}

	protected void verifyNomeGeoArea(String nome) throws ResposeBusinessException {

		String nomeGeoArea = nome.toUpperCase();

		if (nomeGeoArea.contains("TESTE")) {
			throw new ResposeBusinessException(containsTeste);
		}
	}

}
