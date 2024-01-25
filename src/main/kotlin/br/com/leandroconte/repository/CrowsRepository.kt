package br.com.leandroconte.repository

import br.com.leandroconte.models.CrowMail
import br.com.leandroconte.models.CrowMailEntity

interface CrowsRepository {
    fun allCrowsMail(): List<CrowMailEntity>
    fun crowMail(id: Long): CrowMailEntity?
    fun addCrowMail(crowMail: CrowMail): CrowMailEntity
}
