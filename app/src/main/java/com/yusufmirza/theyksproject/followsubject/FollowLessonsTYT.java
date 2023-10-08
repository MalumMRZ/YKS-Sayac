package com.yusufmirza.theyksproject.followsubject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.yusufmirza.theyksproject.R;

import java.util.ArrayList;

public class FollowLessonsTYT extends Fragment {

    Context context;
    ViewPager2 viewPager2;
    ArrayList<Fragment> fragmentLessonArrayList;

    TabLayout tabLayout;
    FragmentActivity fragmentActivity;

    public FollowLessonsTYT(Context context, FragmentActivity fragmentActivity) {
        this.context = context;
        this.fragmentActivity = fragmentActivity;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.follow_your_lessons_tyt, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentLessonArrayList = new ArrayList<>();
        tabLayout = view.findViewById(R.id.tabLayoutLessons);
        viewPager2 = view.findViewById(R.id.lesson_frame_viewPager2);



        Fragment turkishFragment = new LessonFragment(context,setTurkishArrayList(),fragmentActivity,"TÜRKÇE");
        Fragment historyFragment = new LessonFragment(context,setHistoryArrayList(),fragmentActivity,"TARİH");
        Fragment geographyFragment = new LessonFragment(context,setGeographyArrayList(),fragmentActivity,"COĞRAFYA");
        Fragment philosophyFragment = new LessonFragment(context,setPhylisophyArrayList(),fragmentActivity,"FELSEFE");
        Fragment religionFragment = new LessonFragment(context,setReligionArrayList(),fragmentActivity,"DİN");
        Fragment mathsFragment = new LessonFragment(context,setMathsArrayList(),fragmentActivity,"MATEMATİK");
        Fragment geometryFragment = new LessonFragment(context,setGeometryList(),fragmentActivity,"GEOMETRİ");
        Fragment physicsFragment = new LessonFragment(context,setPhysicsArrayList(),fragmentActivity,"FİZİK");
        Fragment chemistryFragment = new LessonFragment(context,setChemistryArrayList(),fragmentActivity,"KİMYA");
        Fragment biologyFragment = new LessonFragment(context,setBiologyArrayList(),fragmentActivity,"BİYOLOJİ");


        fragmentLessonArrayList.add(turkishFragment);
        fragmentLessonArrayList.add(historyFragment);
        fragmentLessonArrayList.add(geographyFragment);
        fragmentLessonArrayList.add(philosophyFragment);
        fragmentLessonArrayList.add(religionFragment);
        fragmentLessonArrayList.add(mathsFragment);
        fragmentLessonArrayList.add(geometryFragment);
        fragmentLessonArrayList.add(physicsFragment);
        fragmentLessonArrayList.add(chemistryFragment);
        fragmentLessonArrayList.add(biologyFragment);

        ViewPagerAdapterForLessons viewPagerAdapterForLessons = new ViewPagerAdapterForLessons(fragmentActivity, fragmentLessonArrayList);

        viewPager2.setAdapter(viewPagerAdapterForLessons);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                if (position == 0) {
                    tab.setText("Türkçe");
                } else if (position == 1) {
                    tab.setText("Tarih");
                } else if (position == 2) {
                    tab.setText("Coğrafya");
                } else if (position == 3) {
                    tab.setText("Felsefe");
                } else if (position == 4) {
                    tab.setText("Din");
                } else if (position == 5) {
                    tab.setText("Matematik");
                } else if (position == 6) {
                    tab.setText("Geometri");
                } else if (position == 7) {
                    tab.setText("Fizik");
                } else if (position == 8) {
                    tab.setText("Kimya");
                } else if (position == 9) {
                    tab.setText("Biyoloji");
                }


            }
        }).attach();
    }

    public ArrayList<Subject> setHistoryArrayList(){

        ArrayList<Subject> historySubjects = new ArrayList<>();

        Subject subject = new Subject("1. Tarih ve Zaman");
        historySubjects.add(subject);
        Subject subject1 = new Subject("2. İnsanlığın İlk Dönemleri");
        historySubjects.add(subject1);
        Subject subject2 = new Subject("3. Ortaçağ’da Dünya");
        historySubjects.add(subject2);
        Subject subject3 = new Subject("4. İlk ve Orta Çağlarda Türk Dünyası");
        historySubjects.add(subject3);
        Subject subject4 = new Subject("5. İslam Medeniyetinin Doğuşu");
        historySubjects.add(subject4);
        Subject subject5 = new Subject("6. İlk Türk İslam Devletleri");
        historySubjects.add(subject5);
        Subject subject6 = new Subject("7.  Yerleşme ve Devletleşme Sürecinde Selçuklu Türkiyesi");
        historySubjects.add(subject6);
        Subject subject7 = new Subject("8. Beylikten Devlete Osmanlı Siyaseti(1300-1453)");
        historySubjects.add(subject7);
        Subject subject8 = new Subject("9. Dünya Gücü Osmanlı Devleti (1453-1600)");
        historySubjects.add(subject8);
        Subject subject9 = new Subject("10. Yeni Çağ Avrupa Tarihi");
        historySubjects.add(subject9);
        Subject subject10 = new Subject("11. Yakın Çağ Avrupa Tarihi");
        historySubjects.add(subject10);
        Subject subject11 = new Subject("12. Osmanlı Devletinde Arayış Yılları");
        historySubjects.add(subject11);
        Subject subject12 = new Subject("13. 18. Yüzyılda Değişim ve Diplomasi");
        historySubjects.add(subject12);
        Subject subject13 = new Subject("14.  En Uzun Yüzyıl");
        historySubjects.add(subject13);
        Subject subject14 = new Subject("15. Osmanlı Kültür ve Medeniyeti");
        historySubjects.add(subject14);
        Subject subject15 = new Subject("16. 20. Yüzyılda Osmanlı Devleti");
        historySubjects.add(subject15);
        Subject subject16 = new Subject("17. I. Dünya Savaşı");
        historySubjects.add(subject16);
        Subject subject17 = new Subject("18. Mondros Ateşkesi, İşgaller ve Cemiyetler");
        historySubjects.add(subject17);
        Subject subject18 = new Subject("19. Kurtuluş Savaşına Hazırlık Dönemi");
        historySubjects.add(subject18);
        Subject subject19 = new Subject("20. I. TBMM Dönemi");
        historySubjects.add(subject19);
        Subject subject20 = new Subject("21. Kurtuluş Savaşı ve Antlaşmalar");
        historySubjects.add(subject20);
        Subject subject21 = new Subject("22. II. TBMM Dönemi ve Çok Partili Hayata Geçiş");
        historySubjects.add(subject21);
        Subject subject22 = new Subject("23. Türk İnkılabı");
        historySubjects.add(subject22);
        Subject subject23 = new Subject("24. Atatürk İlkeleri");
        historySubjects.add(subject23);
        Subject subject24 = new Subject("25. Atatürk Dönemi Türk Dış Politikası");
        historySubjects.add(subject24);
        return historySubjects;
    }

    public ArrayList<Subject> setTurkishArrayList(){

        ArrayList<Subject> turkishSubjects = new ArrayList<>();

        ArrayList<SubSubject> subSubjects = new ArrayList<>();
        SubSubject subSubject = new SubSubject("Gerçek Anlam");
        subSubjects.add(subSubject);
        SubSubject subSubject1 = new SubSubject("Sözde Anlam");
        subSubjects.add(subSubject1);
        SubSubject subSubject2 = new SubSubject("Terim Anlam");
        subSubjects.add(subSubject2);
        SubSubject subSubject3 = new SubSubject("Yalan Anlam");
        subSubjects.add(subSubject3);


        Subject subject = new Subject("1. Sözcükte Anlam",subSubjects);
        turkishSubjects.add(subject);
        Subject subject1 = new Subject("2. Cümlede Anlam");
        turkishSubjects.add(subject1);
        Subject subject2 = new Subject("3. Paragrafta Anlam");
        turkishSubjects.add(subject2);
        Subject subject3 = new Subject("4. Yazım Kuralları");
        turkishSubjects.add(subject3);
        Subject subject4 = new Subject("5. Noktalama İşaretleri");
        turkishSubjects.add(subject4);
        Subject subject5 = new Subject("6. Anlatım Bozuklukları");
        turkishSubjects.add(subject5);
        Subject subject6 = new Subject("7. Ses Bilgisi");
        turkishSubjects.add(subject6);
        Subject subject7 = new Subject("8. İsim");
        turkishSubjects.add(subject7);
        Subject subject8 = new Subject("9. Sıfat");
        turkishSubjects.add(subject8);
        Subject subject9 = new Subject("10. Tamlamalar");
        turkishSubjects.add(subject9);
        Subject subject10 = new Subject("11. Zamir");
        turkishSubjects.add(subject10);
        Subject subject11 = new Subject("12. Zarf");
        turkishSubjects.add(subject11);
        Subject subject12 = new Subject("13. Edat-Bağlaç-Ünlem");
        turkishSubjects.add(subject12);
        Subject subject13 = new Subject("14. Fiil");
        turkishSubjects.add(subject13);
        Subject subject14 = new Subject("15. Fiilimsi");
        turkishSubjects.add(subject14);
        Subject subject15 = new Subject("16. Sözcükte Yapı");
        turkishSubjects.add(subject15);
        Subject subject16 = new Subject("17. Cümlenin Ögeleri");
        turkishSubjects.add(subject16);
        Subject subject17 = new Subject("18. Fiillerde Çatı");
        turkishSubjects.add(subject17);
        Subject subject18 = new Subject("19. Cümle Türleri");
        turkishSubjects.add(subject18);

        return turkishSubjects;
    }

    public ArrayList<Subject> setGeographyArrayList(){

        ArrayList<Subject> geographySubjects = new ArrayList<>();
        Subject subject = new Subject("1. Doğa ve İnsan");
        geographySubjects.add(subject);
        Subject subject1 = new Subject("2. Dünya’nın Şekli ve Hareketleri");
        geographySubjects.add(subject1);
        Subject subject2 = new Subject("3. Coğrafi Konum");
        geographySubjects.add(subject2);
        Subject subject3 = new Subject("4. Harita Bilgisi");
        geographySubjects.add(subject3);
        Subject subject4 = new Subject("5. Atmosfer ve Sıcaklık");
        geographySubjects.add(subject4);
        Subject subject5 = new Subject("6. İklimler");
        geographySubjects.add(subject5);
        Subject subject6 = new Subject("7. Basınç ve Rüzgarlar");
        geographySubjects.add(subject6);
        Subject subject7 = new Subject("8. Nem, Yağış ve Buharlaşma");
        geographySubjects.add(subject7);
        Subject subject8 = new Subject("9. İç Kuvvetler / Dış Kuvvetler");
        geographySubjects.add(subject8);
        Subject subject9 = new Subject("10. Su – Toprak ve Bitkiler");
        geographySubjects.add(subject9);
        Subject subject10 = new Subject("11. Nüfus");
        geographySubjects.add(subject10);
        Subject subject11 = new Subject("12. Göç");
        geographySubjects.add(subject11);
        Subject subject12 = new Subject("13. Yerleşme");
        geographySubjects.add(subject12);
        Subject subject13 = new Subject("14. Türkiye’nin Yer Şekilleri");
        geographySubjects.add(subject13);
        Subject subject14 = new Subject("15. Ekonomik Faaliyetler\n");
        geographySubjects.add(subject14);
        Subject subject15 = new Subject("16. Bölgeler");
        geographySubjects.add(subject15);
        Subject subject16 = new Subject("17. Uluslararası Ulaşım Hatları");
        geographySubjects.add(subject16);
        Subject subject17 = new Subject("18. Çevre ve Toplum");
        geographySubjects.add(subject17);
        Subject subject18 = new Subject("19. Doğal Afetler");
        geographySubjects.add(subject18);

        return geographySubjects;
    }

    public ArrayList<Subject> setPhylisophyArrayList(){

        ArrayList<Subject> phylisophySubjects = new ArrayList<>();
        Subject subject = new Subject("1. Felsefenin Konusu");
        phylisophySubjects.add(subject);
        Subject subject1 = new Subject("2. Bilgi Felsefesi");
        phylisophySubjects.add(subject1);
        Subject subject2 = new Subject("3. Varlık Felsefesi");
        phylisophySubjects.add(subject2);
        Subject subject3 = new Subject("4. Ahlak Felsefesi");
        phylisophySubjects.add(subject3);
        Subject subject4 = new Subject("5. Sanat Felsefesi");
        phylisophySubjects.add(subject4);
        Subject subject5 = new Subject("6. Din Felsefesi");
        phylisophySubjects.add(subject5);
        Subject subject6 = new Subject("7. Siyaset Felsefesi");
        phylisophySubjects.add(subject6);
        Subject subject7 = new Subject("8. Bilim Felsefesi");
        phylisophySubjects.add(subject7);

        return phylisophySubjects;
    }

    public ArrayList<Subject> setReligionArrayList() {
        ArrayList<Subject> religionSubjects = new ArrayList<>();
        Subject subject = new Subject("1. Allah, İnsan İlişkisi");
        religionSubjects.add(subject);
        Subject subject1 = new Subject("2. Dünya ve Ahiret");
        religionSubjects.add(subject1);
        Subject subject2 = new Subject("3. Kur’an’a Göre Hz. Muhammed");
        religionSubjects.add(subject2);
        Subject subject3 = new Subject("4. Kur’an’da Bazı Kavramlar");
        religionSubjects.add(subject3);
        Subject subject4 = new Subject("5. Kur’an’dan Mesajlar");
        religionSubjects.add(subject4);
        Subject subject5 = new Subject("6. İnançla İlgili Meseleler");
        religionSubjects.add(subject5);
        Subject subject6 = new Subject("7. İslam ve Bilim");
        religionSubjects.add(subject6);
        Subject subject7 = new Subject("8. Anadolu’da İslam");
        religionSubjects.add(subject7);
        Subject subject8 = new Subject("9. İslam Düşüncesinde Tasavvufi Yorumlar ve Mezhepler");
        religionSubjects.add(subject8);
        Subject subject9 = new Subject("10. Güncel Dini Meseleler");
        religionSubjects.add(subject9);
        Subject subject10 = new Subject("11. Hint ve Çin Dinleri");
        religionSubjects.add(subject10);

        return religionSubjects;
    }

    public ArrayList<Subject> setMathsArrayList(){
        ArrayList<Subject> mathSubjects = new ArrayList<>();

        Subject subject = new Subject("1. Temel Kavramlar");
        mathSubjects.add(subject);
        Subject subject1 = new Subject("2. Sayı Basamakları");
        mathSubjects.add(subject1);
        Subject subject2 = new Subject("3. Bölme ve Bölünebilme");
        mathSubjects.add(subject2);
        Subject subject3 = new Subject("4. EBOB – EKOK");
        mathSubjects.add(subject3);
        Subject subject4 = new Subject("5. Rasyonel Sayılar");
        mathSubjects.add(subject4);
        Subject subject5 = new Subject("6. Basit Eşitsizlikler");
        mathSubjects.add(subject5);
        Subject subject6 = new Subject("7. Mutlak Değer");
        mathSubjects.add(subject6);
        Subject subject7 = new Subject("8. Üslü Sayılar");
        mathSubjects.add(subject7);
        Subject subject8 = new Subject("9. Köklü Sayılar");
        mathSubjects.add(subject8);
        Subject subject9 = new Subject("10. Çarpanlara Ayırma");
        mathSubjects.add(subject9);
        Subject subject10 = new Subject("11. Oran Orantı");
        mathSubjects.add(subject10);
        Subject subject11 = new Subject("12. Denklem Çözme");
        mathSubjects.add(subject11);
        Subject subject12 = new Subject("13. Problemler");
        mathSubjects.add(subject12);
        Subject subject13 = new Subject("14. Kümeler – Kartezyen Çarpım");
        mathSubjects.add(subject13);
        Subject subject14 = new Subject("15. Mantık");
        mathSubjects.add(subject14);
        Subject subject15 = new Subject("16. Fonskiyonlar");
        mathSubjects.add(subject15);
        Subject subject16 = new Subject("17. Polinomlar");
        mathSubjects.add(subject16);
        Subject subject17 = new Subject("18. 2.Dereceden Denklemler");
        mathSubjects.add(subject17);
        Subject subject18 = new Subject("19. Permütasyon ve Kombinasyon");
        mathSubjects.add(subject18);
        Subject subject19 = new Subject("20. Olasılık");
        mathSubjects.add(subject19);
        Subject subject20 = new Subject("21. Veri – İstatistik");
        mathSubjects.add(subject20);

        return mathSubjects;
    }

    public ArrayList<Subject> setPhysicsArrayList(){
        ArrayList<Subject> physicsSubjects = new ArrayList<>();
        Subject subject = new Subject("1. Fizik Bilimine Giriş");
        physicsSubjects.add(subject);
        Subject subject1 = new Subject("2. Madde ve Özellikleri");
        physicsSubjects.add(subject1);
        Subject subject2 = new Subject("3. Sıvıların Kaldırma Kuvveti");
        physicsSubjects.add(subject2);
        Subject subject3 = new Subject("4. Basınç");
        physicsSubjects.add(subject3);
        Subject subject4 = new Subject("5. Isı, Sıcaklık ve Genleşme");
        physicsSubjects.add(subject4);
        Subject subject5 = new Subject("6. Hareket ve Kuvvet");
        physicsSubjects.add(subject5);
        Subject subject6 = new Subject("7. Dinamik");
        physicsSubjects.add(subject6);
        Subject subject7 = new Subject("8. İş, Güç ve Enerji");
        physicsSubjects.add(subject7);
        Subject subject8 = new Subject("9. Elektrik");
        physicsSubjects.add(subject8);
        Subject subject9 = new Subject("10. Manyetizma");
        physicsSubjects.add(subject9);
        Subject subject10 = new Subject("11. Dalgalar");
        physicsSubjects.add(subject10);
        Subject subject11 = new Subject("12. Optik");
        physicsSubjects.add(subject11);

        return physicsSubjects;
    }

    public ArrayList<Subject> setChemistryArrayList(){

        ArrayList<Subject> chemistrySubjects = new ArrayList<>();
        Subject subject = new Subject("1. Kimya Bilimi");
        chemistrySubjects.add(subject);
        Subject subject1 = new Subject("2. Atom ve Periyodik Sistem");
        chemistrySubjects.add(subject1);
        Subject subject2 = new Subject("3. Kimyasal Türler Arası Etkileşimler");
        chemistrySubjects.add(subject2);
        Subject subject3 = new Subject("4. Maddenin Halleri");
        chemistrySubjects.add(subject3);
        Subject subject4 = new Subject("5. Doğa ve Kimya");
        chemistrySubjects.add(subject4);
        Subject subject5 = new Subject("6. Kimyanın Temel Kanunları");
        chemistrySubjects.add(subject5);
        Subject subject6 = new Subject("7. Kimyasal Hesaplamalar");
        chemistrySubjects.add(subject6);
        Subject subject7 = new Subject("8. Karışımlar");
        chemistrySubjects.add(subject7);
        Subject subject8 = new Subject("9. Asit, Baz ve Tuz");
        chemistrySubjects.add(subject8);
        Subject subject9 = new Subject("10. Kimya Her Yerde");
        chemistrySubjects.add(subject9);

        return chemistrySubjects;
    }

    public ArrayList<Subject> setBiologyArrayList(){
        ArrayList<Subject> biologySubjects = new ArrayList<>();
        Subject subject = new Subject("1. Canlıların Ortak Özellikleri");
        biologySubjects.add(subject);
        Subject subject1 = new Subject("2. Canlıların Temel Bileşenleri");
        biologySubjects.add(subject1);
        Subject subject2 = new Subject("3. Hücre ve Organeller – Madde Geçişleri");
        biologySubjects.add(subject2);
        Subject subject3 = new Subject("4. Canlıların Sınıflandırılması");
        biologySubjects.add(subject3);
        Subject subject4 = new Subject("5. Hücrede Bölünme – Üreme");
        biologySubjects.add(subject4);
        Subject subject5 = new Subject("6. Kalıtım");
        biologySubjects.add(subject5);
        Subject subject6 = new Subject("7. Bitki Biyolojisi");
        biologySubjects.add(subject6);
        Subject subject7 = new Subject("8. Ekosistem");
        biologySubjects.add(subject7);

        return biologySubjects;
    }

    public ArrayList<Subject> setGeometryList(){

        ArrayList<Subject> geometrySubjects = new ArrayList<>();

        Subject subject = new Subject("1. Temel Kavramlar");
        geometrySubjects.add(subject);
        Subject subject1 = new Subject("2. Doğruda Açılar");
        geometrySubjects.add(subject1);
        Subject subject2 = new Subject("3. Üçgende Açılar");
        geometrySubjects.add(subject2);
        Subject subject3 = new Subject("4. Özel Üçgenler");
        geometrySubjects.add(subject3);
        Subject subject4 = new Subject("5. Dik Üçgen");
        geometrySubjects.add(subject4);
        Subject subject5 = new Subject("6. İkizkenar Üçgen");
        geometrySubjects.add(subject5);
        Subject subject6 = new Subject("7. Eşkenar Üçgen");
        geometrySubjects.add(subject6);
        Subject subject7 = new Subject("8. Üçgende Alan");
        geometrySubjects.add(subject7);
        Subject subject8 = new Subject("9. Açıortay");
        geometrySubjects.add(subject8);
        Subject subject9 = new Subject("10. Kenarortay");
        geometrySubjects.add(subject9);
        Subject subject10 = new Subject("11. Üçgende Benzerlik");
        geometrySubjects.add(subject10);
        Subject subject11 = new Subject("12. Açı Kenar Bağıntıları");
        geometrySubjects.add(subject11);
        Subject subject12 = new Subject("13. Çokgenler");
        geometrySubjects.add(subject12);
        Subject subject13 = new Subject("14. Dikdörtgen");
        geometrySubjects.add(subject13);
        Subject subject14 = new Subject("15. Özel Dörtgenler");
        geometrySubjects.add(subject14);
        Subject subject15 = new Subject("16. Çemberler");
        geometrySubjects.add(subject15);
        Subject subject16 = new Subject("17. Katı Cisimler");
        geometrySubjects.add(subject16);
        Subject subject17 = new Subject("18. Prizmalar");
        geometrySubjects.add(subject17);
        Subject subject18 = new Subject("19. Noktanın Analitiği");
        geometrySubjects.add(subject18);
        Subject subject19 = new Subject("20. Doğrunun Analitiği");
        geometrySubjects.add(subject19);
        Subject subject20 = new Subject("21. Çemberin Analitiği");
        geometrySubjects.add(subject20);
        Subject subject21 = new Subject("22. Trigonometri");
        geometrySubjects.add(subject21);

        return geometrySubjects;

    }

}

