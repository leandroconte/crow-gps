package br.com.leandroconte.dao

import br.com.leandroconte.models.CrowMail
import br.com.leandroconte.models.CrowMailEntity

interface CrowsDAO {
    fun allCrowsMail(): List<CrowMailEntity>
    fun crowMail(id: Long): CrowMailEntity?
    fun addCrowMail(crowMail: CrowMail)
}
